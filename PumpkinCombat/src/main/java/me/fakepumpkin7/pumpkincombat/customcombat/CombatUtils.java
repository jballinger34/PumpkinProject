package me.fakepumpkin7.pumpkincombat.customcombat;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.event.CustomDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class CombatUtils {

    public static long knockBackCooldownMS = 500;

    public static void dealDamage(Entity target, double finalDamage, double targetMaxHealth){
        if(target instanceof LivingEntity){
            LivingEntity le = (LivingEntity) target;

            double damageToDeal = finalDamage*le.getMaxHealth() / targetMaxHealth;
            le.damage(damageToDeal);
        }
    }

    public static void dealKnockback(CustomDamageEvent e){
        Entity target = e.getTarget();
        Entity attacker = e.getAttacker();

        Vector distVec = target.getLocation().subtract(attacker.getLocation()).toVector();
        Vector normDistVec =  distVec.normalize();
        target.setVelocity( target.getVelocity().add (normDistVec) );

        //sets to when cooldown ends
        target.setMetadata("pumpkin-last-knockback", new FixedMetadataValue(PumpkinCombat.getInstance(), System.currentTimeMillis() + knockBackCooldownMS));


    }
}

