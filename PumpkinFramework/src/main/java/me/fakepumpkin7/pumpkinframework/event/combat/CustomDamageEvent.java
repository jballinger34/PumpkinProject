package me.fakepumpkin7.pumpkinframework.event.combat;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomDamageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    EntityDamageEvent.DamageCause damageCause;
    @Getter Entity attacker;
    @Getter Entity target;

    @Getter double vanillaDamage;

    public CustomDamageEvent(EntityDamageEvent.DamageCause cause, Entity attacker, Entity target, double vanillaDamage){
        this.damageCause = cause;
        this.attacker = attacker;
        this.target = target;
        this.vanillaDamage = vanillaDamage;

    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }





}
