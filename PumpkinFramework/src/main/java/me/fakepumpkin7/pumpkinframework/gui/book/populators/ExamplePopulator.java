package me.fakepumpkin7.pumpkinframework.gui.book.populators;


import me.fakepumpkin7.pumpkinframework.gui.book.util.BookBuilder;
import me.fakepumpkin7.pumpkinframework.gui.book.util.TextFormat;
import org.bukkit.ChatColor;

public class ExamplePopulator implements BookPopulator {

    @Override
    public String getBookContents() {
        return new BookBuilder()
                .addText("Mythic Runes\n")
                .format(ChatColor.GOLD)
                .format(TextFormat.BOLD)
                .addText("Click any of the\n")
                .addText("following Mythic runes\n")
                .addText("for more information")
                .addText("\n")
                .addText("1. ").addText("Dodge\n").format(ChatColor.GRAY)
                .addText("2. ").addText("Reinforced\n").format(ChatColor.GRAY)
                .addText("3. ").addText("Fearless\n").format(ChatColor.GRAY)
                .addText("4. ").addText("Unholy\n").format(ChatColor.GRAY)
                .addText("5. ").addText("Demonic Aura\n").format(ChatColor.GRAY)
                .addText("6. ").addText("Rift Control\n").format(ChatColor.GRAY)
                .addText("7. ").addText("Thanksgiving\n").format(ChatColor.GRAY)

                .addPage()

                .addText("Mythic Runes\n")
                .format(ChatColor.GOLD)
                .format(TextFormat.BOLD)
                .addText("Click any of the\n")
                .addText("following Mythic runes\n")
                .addText("for more information")
                .addText("\n")
                .addText("8. ").addText("Flame Elemental\n").format(ChatColor.GRAY)
                .addText("9. ").addText("Auto Loot\n").format(ChatColor.GRAY)
                .addText("10. ").addText("Spectral\n").format(ChatColor.GRAY)
                .addText("11. ").addText("Shadowstep\n").format(ChatColor.GRAY)
                .addText("12. ").addText("Wolves\n").format(ChatColor.GRAY)

                .build();
    }
}