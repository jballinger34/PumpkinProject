package me.fakepumpkin7.pumpkinframework.event.combat;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomVanillaDamageEvent extends AbstractCustomDamageEvent {


    public CustomVanillaDamageEvent(Entity target, EntityDamageEvent.DamageCause cause, double vanillaDamage){
        this.damageCause = cause;
        this.target = target;
        this.finalDamage = workOutDamage(cause, vanillaDamage);

    }



    private double workOutDamage(EntityDamageEvent.DamageCause cause, double vanillaDamage){
        //TODO
        // make a switch statement for different damage causes
        return vanillaDamage;
    }


}
