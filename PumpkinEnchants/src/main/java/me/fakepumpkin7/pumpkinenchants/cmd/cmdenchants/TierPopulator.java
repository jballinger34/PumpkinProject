package me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants;


import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinframework.gui.book.populators.BookPopulator;
import me.fakepumpkin7.pumpkinframework.gui.book.util.BookBuilder;
import me.fakepumpkin7.pumpkinframework.gui.book.util.TextFormat;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.ArrayDeque;
import java.util.Deque;

public class TierPopulator implements BookPopulator {

    private final ItemRarity rarity;

    //Only write the book once.
    //Contents do not change.
    private String cachedContents = null;

    public TierPopulator(ItemRarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public String getBookContents() {
        if (cachedContents != null) {
            return cachedContents;
        }

        BookBuilder bookBuilder = new BookBuilder();

        addHeader(bookBuilder);

        Deque<EnchantType> tieredEnchantments = getEnchantmentsInTier(rarity);
        int i = 0;
        for (EnchantType enchantmentType : tieredEnchantments) {

            bookBuilder
                    .addText((++i) + ". ").format(ChatColor.GRAY)
                    .format(TextFormat.RUN_COMMAND, "/enchants " + enchantmentType.getEnchant().name().toLowerCase())
                    .addText(enchantmentType.getEnchant().name() + "\n").format(ChatColor.DARK_GRAY)
                    .format(TextFormat.RUN_COMMAND, "/enchants " + enchantmentType.getEnchant().name().toLowerCase());

            //Can only fit 7 on a page neatly.
            if (i % 7 == 0 || i == tieredEnchantments.size()) {
                if (i == tieredEnchantments.size()) {
                    int lineNum = i % 7;
                    while (++lineNum < 9) {
                        bookBuilder.addText("\n");
                    }
                } else {
                    bookBuilder.addText("\n");
                }

                bookBuilder
                        .addText("           ").addText("BACK").format(ChatColor.RED).format(TextFormat.BOLD)
                        .format(TextFormat.RUN_COMMAND, "/enchants");

                if (i != tieredEnchantments.size()) {
                    bookBuilder.addPage();
                    addHeader(bookBuilder);
                }
            }
        }

        return (cachedContents = bookBuilder.build());
    }

    private void addHeader(BookBuilder bookBuilder) {
        DocumentationUtil.addTierFormat(bookBuilder, rarity, rarity.name() + " Runes\n");

        bookBuilder.addText("Click any of the following runes for more information.\n\n");
    }

    public Deque<EnchantType> getEnchantmentsInTier(ItemRarity rarity) {
        Deque<EnchantType> enchantments = new ArrayDeque<>();
        for (EnchantType enchantmentType : EnchantType.values()) {
            if (enchantmentType.getRarity() == rarity) {
                enchantments.add(enchantmentType);
            }
        }

        return enchantments;
    }


}
