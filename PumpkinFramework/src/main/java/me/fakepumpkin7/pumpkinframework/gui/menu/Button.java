package me.fakepumpkin7.pumpkinframework.gui.menu;

import me.fakepumpkin7.pumpkinframework.gui.menu.actions.ClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Button {

    public Button(ItemStack itemStack, ClickAction action){
        this.itemStack = itemStack;
        this.clickAction = action;
    }
    ItemStack itemStack;
    ClickAction clickAction;

    public void click(Player player, InventoryClickEvent event){
        clickAction.click(player, event);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
