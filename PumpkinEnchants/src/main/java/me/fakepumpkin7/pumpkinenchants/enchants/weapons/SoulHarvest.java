package me.fakepumpkin7.pumpkinenchants.enchants.weapons;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SoulHarvest extends BaseEnchant {

    public SoulHarvest() {
        super("Soul Harvest","Restore health and hunger when you kill a player. ", EnchantmentGroup.WEAPONS, 4, false);
    }

    double healthPerLevel = 1;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if(target.getHealth() - event.getFinalDamage() <= 0){
            CombatUtils.healEntity(user, healthPerLevel*enchantLevel);

            if(!(user instanceof Player)) return;
            Player player = (Player) user;
            player.setFoodLevel(20);
        }

    }
}
