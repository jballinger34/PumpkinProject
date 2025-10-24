package me.fakepumpkin7.pumpkinframework.items.interactive;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InteractiveItem {

    ItemStack getItem();
    void onInteract(Player player);
    void onDragDrop(Player player, ItemStack droppedTo);
}
