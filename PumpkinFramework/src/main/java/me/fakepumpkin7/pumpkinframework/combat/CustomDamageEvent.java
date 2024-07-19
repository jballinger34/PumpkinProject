package me.fakepumpkin7.pumpkinframework.combat;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CustomDamageEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean isCancelled;

    public CustomDamageEvent(EntityDamageEvent.DamageCause cause, Entity attacker, Entity target){
        this.damageCause = cause;

        this.attacker = attacker;

        this.target = target;

        this.damage = workOutDamage(attacker);

        this.knockback = workOutKnockback(attacker,target);
    }


    @Getter
    protected EntityDamageEvent.DamageCause damageCause;
    @Getter
    protected Entity target;

    @Getter
    protected double damage;
    @Getter
    private Entity attacker;
    @Getter
    private double knockback;

    public double workOutFinalDamage(){
        double scaled = damage* (1-(CombatUtils.getEntityDefence(target)/1000));
        return damage;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    private double workOutDamage(Entity attacker){
        double dmg = 0;

        //entity stats
        double baseDamage = CombatUtils.getEntityBaseDamage(attacker);


        //weapon stats
        if(attacker instanceof Player){
            Player player = (Player) attacker;
            ItemStack weapon = player.getInventory().getItemInHand();
            double weaponBaseDamage = CombatUtils.getItemBaseDamage(weapon);

            dmg = (baseDamage+weaponBaseDamage) ;
            return dmg;
        }

        dmg = baseDamage;

        return dmg;
    }


    private double workOutKnockback(Entity attacker, Entity target){
        double knockback = 1;
        if(attacker instanceof Player) {

            Player player = (Player) attacker;
            ItemStack weapon = player.getInventory().getItemInHand();
            knockback *= CombatUtils.getItemKnockbackMulti(weapon);
            return knockback;
        }
        return knockback;
    }
}
