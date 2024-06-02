package me.fakepumpkin7.pumpkinframework.event.combat;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public abstract class AbstractCustomDamageEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();


    @Getter
    EntityDamageEvent.DamageCause damageCause;
    @Getter
    Entity target;

    @Getter double finalDamage;


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}
}
