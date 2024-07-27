package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;


import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;


public class Healthy extends BaseEnchant {

    public Healthy() {
        super("Healthy","Adds Health" , EnchantmentGroup.ARMOR, 5,true);
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
