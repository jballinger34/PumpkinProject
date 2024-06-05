package me.fakepumpkin7.pumpkinframework.gui.buttons;

import me.fakepumpkin7.pumpkinframework.gui.Button;
import me.fakepumpkin7.pumpkinframework.gui.types.PagedMenu;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BackButton extends Button {

    private static final ItemStack PREVIOUS_BUTTON = new ItemBuilder(Material.ARROW)
            .setName("&c&lPrevious Page")
            .addLoreLine("&7Click to view the previous page")
            .build();

    public BackButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getPrevious() != null) {
                menu.getPrevious().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() - 1);
            }
        });
    }

    public BackButton(PagedMenu menu) {
        this(PREVIOUS_BUTTON, menu);
    }

}
