package me.fakepumpkin7.pumpkinframework.event.combat;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CustomDamageEvent extends AbstractCustomDamageEvent{

    @Getter
    private Entity attacker;
    @Getter
    private double knockback;



    public CustomDamageEvent(EntityDamageEvent.DamageCause cause, Entity attacker, Entity target){
        this.damageCause = cause;

        this.attacker = attacker;
        this.target = target;

        double targetDefence = workOutDefence(target);

        double damage = workOutDamage(attacker);
        this.finalDamage = scaleDamage(damage, targetDefence);

        this.knockback = workOutKnockback(attacker,target);




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
