package me.fakepumpkin7.pumpkinframework.event.combat;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CustomDamageEvent extends AbstractCustomDamageEvent {

    @Getter
    Entity attacker;



    public CustomDamageEvent(EntityDamageEvent.DamageCause cause, Entity attacker, Entity target){
        this.damageCause = cause;

        this.attacker = attacker;
        this.target = target;

        double targetDefence = workOutDefence(target);
        double targetMaxHealth = CombatUtils.getEntityMaxHealth(target);

        double damage = workOutDamage(attacker);
        this.finalDamage = scaleDamage(damage, targetDefence);

        CombatUtils.dealTrueDamage(target, finalDamage);
        CombatUtils.dealKnockback(target, attacker, workOutKnockback(attacker, target));

    }


    private double workOutDamage(Entity attacker){
        double dmg = 0;

        //entity stats
        double baseDamage = CombatUtils.getEntityBaseDamage(attacker);
        //System.out.println("pbd:" + playerBaseDamage);
        double damageMultiplier = CombatUtils.getEntityDamageMulti(attacker);
        //System.out.println("pdm:" + playerDamageMultiplier);

        //weapon stats
        if(attacker instanceof Player){
            Player player = (Player) attacker;
            ItemStack weapon = player.getInventory().getItemInHand();
            double weaponBaseDamage = CombatUtils.getItemBaseDamage(weapon);
            //System.out.println("wbd:" + weaponBaseDamage);
            double weaponDamageMultiplier = CombatUtils.getItemDamageMulti(weapon);
            //System.out.println("wdm:" + weaponDamageMultiplier);

            dmg = (baseDamage+weaponBaseDamage)*damageMultiplier*weaponDamageMultiplier ;
            return dmg;
        }

        dmg = baseDamage*damageMultiplier;
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

    private double getVanillaHealth(Entity entity){
        if(entity instanceof Creature){
            Creature creature = (Creature) entity;
            return creature.getMaxHealth();
        }
        return 1;
    }


    private double workOutDefence(Entity entity){
        double defence = 0;
        if(entity.hasMetadata("pumpkin-custom-defence")){
            defence = entity.getMetadata("pumpkin-custom-defence").get(0).asDouble();
        }
        return defence;
    }

    private double scaleDamage(double damage, double defence){
        double scaled = damage* (1-(defence/1000));
        return scaled;
    }



}
