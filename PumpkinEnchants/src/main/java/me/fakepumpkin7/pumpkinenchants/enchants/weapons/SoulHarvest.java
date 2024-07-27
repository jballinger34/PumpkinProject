package me.fakepumpkin7.pumpkinenchants.enchants.weapons;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SoulHarvest extends BaseEnchant {

    public SoulHarvest() {
        super("Soul Harvest","Restore health and hunger when you kill a player. ", EnchantmentGroup.WEAPONS, 4, false);
    }

    double healthPerLevel = 10;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        if(CombatUtils.getEntityCustomHealth(target) - event.workOutDamageAfterDefence() <= 0){
            CombatUtils.healEntity(user, healthPerLevel*enchantLevel);

            if(!(user instanceof Player)) return;
            Player player = (Player) user;
            player.setFoodLevel(20);
        }

    }
}
