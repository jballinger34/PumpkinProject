package me.fakepumpkin7.pumpkinframework.gui.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseAction {
    void close(Player player, InventoryCloseEvent event);
}
