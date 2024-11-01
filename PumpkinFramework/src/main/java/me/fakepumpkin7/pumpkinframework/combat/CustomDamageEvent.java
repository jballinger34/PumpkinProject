package me.fakepumpkin7.pumpkinframework.combat;

import lombok.Getter;
import lombok.Setter;
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

    public CustomDamageEvent(EntityDamageEvent.DamageCause cause, Entity attacker, Entity target, double vanillaDamage){

        this.damageCause = cause;

        this.attacker = attacker;

        this.target = target;

        if(attacker != null) {
            this.damage = workOutDamage(attacker);

            this.knockback = workOutKnockback(attacker, target);
        } else {
            this.damage = vanillaDamage;
        }


        this.target = target;

    }


    @Getter
    protected EntityDamageEvent.DamageCause damageCause;
    @Getter
    protected Entity target;

    @Getter @Setter
    protected double damage;
    @Getter
    private Entity attacker;
    @Getter
    private double knockback;

    public double workOutDamageAfterDefence(){
        double scaled = damage * (100/(CombatUtils.getEntityDefence(target)+100));
        return scaled;
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
