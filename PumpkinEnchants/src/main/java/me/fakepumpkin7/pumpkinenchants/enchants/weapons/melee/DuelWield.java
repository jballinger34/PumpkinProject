package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DuelWield extends BaseEnchant {
    public DuelWield() {
        super("Duel Wield","Chance to proc a second damage event.", EnchantmentGroup.MELEE_WEAPONS, 3);
    }

    double procChance = 0.01;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        double random = Math.random();
        double chance = procChance * enchantLevel;

        if(random < chance) {
            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(EntityDamageEvent.DamageCause.CUSTOM, user, target, event.getDamage()));
        }

    }

}