package me.fakepumpkin7.pumpkinframework.armor.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ArmorUnEquipEvent extends Event implements Cancellable {

    public ArmorUnEquipEvent(Player player, ItemStack itemStack){
        this.player = player;
        this.item = itemStack;
    }
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }
}