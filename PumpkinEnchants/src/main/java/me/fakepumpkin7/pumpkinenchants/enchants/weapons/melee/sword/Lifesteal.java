package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;


public class Lifesteal extends BaseEnchant {
    public Lifesteal() {
        super("Lifesteal","Steals Life :D", EnchantmentGroup.SWORD, 3);
    }
    double healthPerProc = 1;
    double procChance = 0.1;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        double random = Math.random();
        double chance = procChance * enchantLevel;

        if(random < chance) {
            CombatUtils.healEntity(user,enchantLevel*healthPerProc);
        }

    }

}
