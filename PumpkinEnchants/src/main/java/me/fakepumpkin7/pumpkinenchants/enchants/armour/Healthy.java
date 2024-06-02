package me.fakepumpkin7.pumpkinenchants.enchants.armour;


import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Player;


public class Healthy extends BaseEnchant {

    public Healthy() {
        super("Healthy", EnchantmentGroup.ARMOR);
        this.max = 5;
    }

    double healthPerLevel = 5;

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        CombatUtils.addEntityHealth(player, enchantLevel*healthPerLevel);
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        CombatUtils.addEntityHealth(player, -enchantLevel*healthPerLevel);
    }
}
