package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;

public class Protection extends BaseEnchant {

    public Protection() {
        super("Protection","Adds Defence" , EnchantmentGroup.ARMOR, 5,true);
    }

    double DefencePerLevel = 5;

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        CombatUtils.addEntityDefence(player, enchantLevel*DefencePerLevel);
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        CombatUtils.addEntityDefence(player, -enchantLevel*DefencePerLevel);
    }
}
