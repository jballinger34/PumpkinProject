package me.fakepumpkin7.pumpkincombat.customcombat;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.event.CustomDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
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

        if(target instanceof Item){
            Item itemEntity = (Item) target;
            itemEntity.remove();
        }
    }

    public static void dealKnockback(CustomDamageEvent e, double knockback){
        Entity target = e.getTarget();
        Entity attacker = e.getAttacker();

        if(attacker == null){
            return;
        }

        if(target.hasMetadata("pumpkin-last-knockback")
                && ( System.currentTimeMillis() < target.getMetadata("pumpkin-last-knockback").get(0).asLong())) {
            return;
        }

        Vector distVec = target.getLocation().subtract(attacker.getLocation()).toVector();
        Vector normDistVec =  distVec.normalize().setY(0);
        Vector scaledDistVec = normDistVec.multiply(knockback);
        target.setVelocity( target.getVelocity().add (scaledDistVec) );

        //sets to when cooldown ends
        target.setMetadata("pumpkin-last-knockback", new FixedMetadataValue(PumpkinCombat.getInstance(), System.currentTimeMillis() + knockBackCooldownMS));
    }
}

