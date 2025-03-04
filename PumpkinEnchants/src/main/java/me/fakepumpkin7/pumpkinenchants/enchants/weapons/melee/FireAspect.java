package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireAspect extends BaseEnchant {
    public FireAspect() {
        super("Fire Aspect","Sets victim on fire", EnchantmentGroup.MELEE_WEAPONS,3);
    }

    int ticksPerLevel = 30;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        target.setFireTicks(ticksPerLevel* enchantLevel);

    }
}
