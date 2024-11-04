package me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants;


import com.rit.sucy.service.ERomanNumeral;
import lombok.Getter;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantItem;
import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.gui.book.populators.BookPopulator;
import me.fakepumpkin7.pumpkinframework.gui.book.util.BookBuilder;
import me.fakepumpkin7.pumpkinframework.gui.book.util.MinecraftFontInfo;
import me.fakepumpkin7.pumpkinframework.gui.book.util.TextFormat;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class EnchantPopulator implements BookPopulator {

    private static final Set<EnchantmentGroup> validStackGroups = EnumSet.noneOf(EnchantmentGroup.class);

    static {
        validStackGroups.add(EnchantmentGroup.ARMOR);
    }

    @Getter
    private final EnchantType enchantmentType;

    //Only write the book once.
    //Contents do not change.
    private String cachedContents = null;

    public EnchantPopulator(EnchantType enchantmentType) {
        this.enchantmentType = enchantmentType;
    }

    @Override
    public String getBookContents() {
        if (cachedContents != null) {
            return cachedContents;
        }


        BookBuilder bookBuilder = new BookBuilder();

        BaseEnchant enchantment = enchantmentType.getEnchant();
        bookBuilder.addText(enchantment.name() + " ").format(TextFormat.BOLD);
        bookBuilder.addText("(I - " + ERomanNumeral.numeralOf(enchantment.getMaxLevel()) + ")\n").format(ChatColor.RED);
        bookBuilder.addText("Tier: ").format(ChatColor.GRAY);
        DocumentationUtil.addTierFormat(bookBuilder, enchantmentType.getRarity(), enchantmentType.getRarity().name());

        bookBuilder.addText("\n\n");

        String description = enchantment.getDescription();

        final int maxLineLength = 85;
        int currentLineLength = -1;
        StringBuilder descriptionBuilder = new StringBuilder();
        for (String word : description.split("[ ]")) {
            int wordLength = 0;
            for (char wordChar : word.toCharArray()) {
                wordLength += MinecraftFontInfo.getDefaultFontInfo(wordChar).getLength();
            }

            currentLineLength += wordLength;

            if (currentLineLength > maxLineLength) {
                descriptionBuilder.append("\n");
                currentLineLength = wordLength;
            }

            descriptionBuilder.append(word).append(" ");
            currentLineLength += 5; //Spaces are worth 5
        }
        {
            String descriptionText = descriptionBuilder.toString();
            bookBuilder.addText(descriptionText);
        }

        //Finish up the description and add a spacer line
        bookBuilder.addText("\n\n");

        bookBuilder.addText("Applicable to:\n");
        for (EnchantmentGroup enchantmentGroup : enchantment.getGroup().getApplicableGroups()) {
            try {
                bookBuilder.addText("- ").format(ChatColor.DARK_GRAY).addText(enchantmentGroup.getPluralName() + "\n").format(ChatColor.GRAY);
            } catch (NullPointerException e) {
                System.out.println("Failed to process enchantment group '" + enchantmentGroup + "'");
            }
        }

        //Add a line for every newline character found
        int lineNumber = bookBuilder.getCurrentPageContents().split("[\n]").length + 1;

        while (++lineNumber < 13) {
            bookBuilder.addText("\n");
        }

        //BUtil.log("Adding back button at possible line " + lineNumber);

        bookBuilder
                .addText("           ").addText("BACK").format(ChatColor.RED).format(TextFormat.BOLD)
                .format(TextFormat.RUN_COMMAND, "/enchants " + enchantmentType.getRarity().name());

        lineNumber = 0;
        //Only talk about stacking if the enchantment can be applied to more than one piece at once
        if (isPossiblyStackable(enchantment.getGroup())) {
            bookBuilder.addPage();

            lineNumber++;
            bookBuilder.addText("Stacking Status: \n").format(TextFormat.BOLD).format(ChatColor.GOLD);
            String stackStatus = "§0Does not stack\n";
            if (enchantment.canStack()) {
                stackStatus = "§0Stacks on multiple\n§0pieces of equipment.\n";
            }

            for (String stackStatusLine : stackStatus.split("[\n]")) {
                bookBuilder.addText(stackStatusLine + "\n");
                lineNumber++;
            }

            lineNumber++;
            bookBuilder.addText("\n"); //Split stack status and the rest of the book
        }

        //


        while (++lineNumber < 14) {
            bookBuilder.addText("\n");
        }

        bookBuilder
                .addText("           ").addText("BACK").format(ChatColor.RED).format(TextFormat.BOLD)
                .format(TextFormat.RUN_COMMAND, "/runes " + enchantmentType.getRarity().name());

        return (cachedContents = bookBuilder.build());
    }

    private boolean isPossiblyStackable(EnchantmentGroup enchantmentGroup) {
        switch (enchantmentGroup) {
            case ARMOR:
                return true;
            default: {
                List<EnchantmentGroup> children = enchantmentGroup.getChildren();
                if (children.get(0) == enchantmentGroup) {
                    return false; //Bottom of the chain.
                }

                for (EnchantmentGroup childGroup : children) {
                    switch (childGroup) {
                        case HELMET:
                        case CHESTPLATE:
                        case LEGGINGS:
                        case BOOTS: {
                            for (EnchantmentGroup armorGroup : Arrays.asList(
                                    EnchantmentGroup.HELMET, EnchantmentGroup.CHESTPLATE, EnchantmentGroup.LEGGINGS, EnchantmentGroup.BOOTS)) {
                                if (armorGroup != childGroup && children.contains(armorGroup)) {
                                    return true;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        return false;
    }

}
