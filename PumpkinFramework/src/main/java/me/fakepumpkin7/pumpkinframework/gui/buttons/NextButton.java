package me.fakepumpkin7.pumpkinframework.gui.buttons;

import me.fakepumpkin7.pumpkinframework.gui.Button;
import me.fakepumpkin7.pumpkinframework.gui.types.PagedMenu;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NextButton extends Button {
    private static final ItemStack NEXT_BUTTON = new ItemBuilder(Material.ARROW)
            .setName("&c&lNext Page")
            .addLoreLine("&7Click to view the next page")
            .build();

    public NextButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getNext() != null) {
                menu.getNext().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() + 1);
            }
        });
    }

    public NextButton(PagedMenu menu) {
        this(NEXT_BUTTON, menu);
    }

}
