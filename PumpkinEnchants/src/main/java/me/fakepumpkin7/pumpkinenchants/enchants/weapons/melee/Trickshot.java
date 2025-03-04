package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Trickshot extends BaseEnchant {

    public Trickshot() {
        super("Trickshot","Chance to get a second, delayed damage event.", EnchantmentGroup.MELEE_WEAPONS, 5);
    }

    double procChance = 0.01;
    int delayTicks = 20;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = procChance * enchantLevel;

        if(random < chance) {

            Bukkit.getScheduler().runTaskLater(
                    PumpkinEnchants.getInstance(),
                    () -> CombatUtils.dealTrueDamage(target, event.getFinalDamage()),
                    delayTicks);
        }

    }

}
