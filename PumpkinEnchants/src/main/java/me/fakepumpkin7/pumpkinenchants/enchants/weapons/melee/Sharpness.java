package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Sharpness extends BaseEnchant {
    public Sharpness() {
        super("Sharpness","Makes sword sharp :D", EnchantmentGroup.WEAPONS,8);
    }
    double damagebonus = 0.1;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        double damage = event.getDamage();
        event.setDamage(damage*(1+(damagebonus*enchantLevel)));
    }
}
