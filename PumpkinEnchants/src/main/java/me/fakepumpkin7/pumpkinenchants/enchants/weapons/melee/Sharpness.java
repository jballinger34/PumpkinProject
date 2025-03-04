package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Sharpness extends BaseEnchant {
    public Sharpness() {
        super("Sharpness","Makes sword sharp :D", EnchantmentGroup.MELEE_WEAPONS,8);
    }
    double damagebonus = 0.1;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double damage = event.getDamage();
        event.setDamage(damage*(1+(damagebonus*enchantLevel)));
    }
}
