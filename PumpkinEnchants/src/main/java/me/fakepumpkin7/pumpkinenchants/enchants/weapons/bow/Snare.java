package me.fakepumpkin7.pumpkinenchants.enchants.weapons.bow;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class Snare extends BaseEnchant {

    public Snare() {
        super("Snare","Chance to stun the enemy.", EnchantmentGroup.BOW, 4);
    }

    double procChancePerLevel = 0.05;
    double stunTime = 2;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        if(!(target instanceof Player)) return;
        Player victim = (Player) target;

        if(CombatUtils.isStun(victim)) return;

        double random = Math.random();
        double chance = procChancePerLevel * enchantLevel;

        if(random < chance) {
            CombatUtils.addStun(victim, stunTime);
        }
    }
}
