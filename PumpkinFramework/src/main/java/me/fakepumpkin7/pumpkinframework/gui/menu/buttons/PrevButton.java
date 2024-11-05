package me.fakepumpkin7.pumpkinframework.gui.menu.buttons;

import me.fakepumpkin7.pumpkinframework.gui.menu.Button;
import me.fakepumpkin7.pumpkinframework.gui.menu.PaginatedMenu;
import me.fakepumpkin7.pumpkinframework.gui.menu.actions.ClickAction;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PrevButton extends Button {
    private static final ItemStack PREVIOUS_BUTTON = new ItemBuilder(Material.ARROW)
            .setName("Previous Page")
            .addLoreLine("Click to view the previous page")
            .build();

    public PrevButton(ItemStack item, PaginatedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getPrevious() != null) {
                menu.getPrevious().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() - 1);
            }
        });
    }

    public PrevButton(PaginatedMenu menu) {
        this(PREVIOUS_BUTTON, menu);
    }

}
