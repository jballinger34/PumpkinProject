package me.fakepumpkin7.pumpkincombat.customcombat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class CombatUtils {

    public static void dealDamage(Entity target, double finalDamage, double targetMaxHealth){
        if(target instanceof LivingEntity){
            LivingEntity le = (LivingEntity) target;

            double damageToDeal = finalDamage*le.getMaxHealth() / targetMaxHealth;
            le.damage(damageToDeal);
        }




    }
}
