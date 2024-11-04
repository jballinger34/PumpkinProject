package me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants;



import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinframework.gui.book.populators.BookPopulator;
import me.fakepumpkin7.pumpkinframework.gui.book.util.BookBuilder;
import me.fakepumpkin7.pumpkinframework.gui.book.util.TextFormat;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;

import java.util.Arrays;

public class MainMenuPopulator implements BookPopulator {

    //Only write the book once.
    //Contents do not change.
    private String cachedContents = null;

    public MainMenuPopulator() {

    }

    @Override
    public String getBookContents() {
        if (cachedContents != null) {
            return cachedContents;
        }

        BookBuilder bookBuilder = new BookBuilder();

        bookBuilder
                .addText("Enchantment Info\n").format(TextFormat.BOLD)
                .addText("Please click one of the following Enchantment categories.\n\n");

        //Add Tiers
        addTierFormat(bookBuilder, ItemRarity.COMMON);
        addTierFormat(bookBuilder, ItemRarity.UNCOMMON);
        addTierFormat(bookBuilder, ItemRarity.RARE);
        addTierFormat(bookBuilder, ItemRarity.MYTHIC);

        //bookBuilder.addPage();
        //addTierFormat(bookBuilder, ItemRarity.SOUL);

        return (cachedContents = bookBuilder.build());
    }

    private void addTierFormat(BookBuilder bookBuilder, ItemRarity rarity) {
        DocumentationUtil.addTierFormat(bookBuilder, rarity, rarity.name());
        bookBuilder.format(TextFormat.UNDERLINE);
        bookBuilder.format(TextFormat.RUN_COMMAND, "/enchants " + rarity.name());

        long sizeOfRarity = Arrays.stream(EnchantType.values()).filter(enchantType -> enchantType.getRarity().equals(rarity)).count();

        bookBuilder.addText(" (" + sizeOfRarity + ")\n\n");
        bookBuilder.format(TextFormat.RUN_COMMAND, "/enchants " + rarity.name());
    }

}
