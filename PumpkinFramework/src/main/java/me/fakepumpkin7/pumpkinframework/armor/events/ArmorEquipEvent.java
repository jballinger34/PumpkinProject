package me.fakepumpkin7.pumpkinframework.armor.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipEvent extends Event implements Cancellable {

    public ArmorEquipEvent(Player player, ItemStack itemStack){
        this.player = player;
        this.item = itemStack;
    }
    private static final HandlerList handlers = new HandlerList();

    @Getter private final Player player;
    @Getter private final ItemStack item;
    @Getter @Setter private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}