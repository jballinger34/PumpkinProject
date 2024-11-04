package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;

public class FireAspect extends BaseEnchant {
    public FireAspect() {
        super("Fire Aspect","Sets victim on fire", EnchantmentGroup.MELEE_WEAPONS,3);
    }

    int ticksPerLevel = 30;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        target.setFireTicks(ticksPerLevel* enchantLevel);

    }
}
