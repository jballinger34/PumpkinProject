package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Speedy extends BaseEnchant {

    public Speedy() {
        super("Speedy","Gives speed" , EnchantmentGroup.BOOTS, 3,false);
    }

    double SpeedPerLevel = 0.04;

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        CombatUtils.addPlayerSpeed(player,(enchantLevel*SpeedPerLevel));
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        CombatUtils.addPlayerSpeed(player,-(enchantLevel*SpeedPerLevel));
    }
}
