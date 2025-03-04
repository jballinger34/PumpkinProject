package me.fakepumpkin7.pumpkinenchants.enchants.weapons.bow;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Snare extends BaseEnchant {

    public Snare() {
        super("Snare","Chance to stun the enemy.", EnchantmentGroup.BOW, 4);
    }

    double procChancePerLevel = 0.05;
    double stunTime = 2;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
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
