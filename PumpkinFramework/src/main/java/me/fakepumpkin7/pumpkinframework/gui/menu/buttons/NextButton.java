package me.fakepumpkin7.pumpkinframework.gui.menu.buttons;

import me.fakepumpkin7.pumpkinframework.gui.menu.Button;
import me.fakepumpkin7.pumpkinframework.gui.menu.PaginatedMenu;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NextButton extends Button {
    private static final ItemStack NEXT_BUTTON = new ItemBuilder(Material.ARROW)
            .setName("Next Page")
            .addLoreLine("Click to view the next page")
            .build();

    public NextButton(ItemStack item, PaginatedMenu menu) {
        super(item, (player, event) -> {

            if (menu.getNext() != null) {
                menu.getNext().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() + 1);
            }
        });
    }

    public NextButton(PaginatedMenu menu) {
        this(NEXT_BUTTON, menu);
    }
}
