package me.fakepumpkin7.pumpkinframework.gui.buttons;

import me.fakepumpkin7.pumpkinframework.gui.Button;
import me.fakepumpkin7.pumpkinframework.gui.types.PagedMenu;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RefreshButton extends Button {

    private static final ItemStack REFRESH_BUTTON = new ItemBuilder(Material.PAPER)
            .setName("&c&lRefresh")
            .addLoreLine("&7Click to refresh the current page")
            .build();

    public RefreshButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            player.closeInventory();
            menu.getCurrent().refresh(player);
        });
    }

    public RefreshButton(PagedMenu menu) {
        this(REFRESH_BUTTON, menu);
    }

}
