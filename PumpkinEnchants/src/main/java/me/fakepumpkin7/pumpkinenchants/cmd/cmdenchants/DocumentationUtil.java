package me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants;

import me.fakepumpkin7.pumpkinframework.gui.book.util.BookBuilder;
import me.fakepumpkin7.pumpkinframework.gui.book.util.TextFormat;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

public class DocumentationUtil {

    public static void addTierFormat(BookBuilder bookBuilder, ItemRarity rarity, String contents) {
        switch (rarity) {
            case COMMON:
                bookBuilder.addText(contents).format(ChatColor.GRAY).format(TextFormat.BOLD);
                break;
            case UNCOMMON:
                bookBuilder.addText(contents).format(ChatColor.DARK_GREEN).format(TextFormat.BOLD);
                break;
            case RARE:
                bookBuilder.addText(contents).format(ChatColor.DARK_AQUA).format(TextFormat.BOLD);
                break;
            case MYTHIC:
                bookBuilder.addText(contents).format(ChatColor.GOLD).format(TextFormat.BOLD);
                break;
            case SOUL:
                bookBuilder.addText(contents).format(ChatColor.RED).format(TextFormat.BOLD);
                break;
        }
    }

}
