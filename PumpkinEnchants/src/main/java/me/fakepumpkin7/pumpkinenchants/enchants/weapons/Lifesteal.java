package me.fakepumpkin7.pumpkinenchants.enchants.weapons;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;


public class Lifesteal extends BaseEnchant {
    public Lifesteal() {
        super("Lifesteal","Steals Life :D",3, EnchantmentGroup.WEAPONS);
    }
    double healthPerProc = 5;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        CombatUtils.healEntity(user,enchantLevel*healthPerProc);
    }

}
