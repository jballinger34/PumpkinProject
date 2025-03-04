package me.fakepumpkin7.pumpkinenchants.enchants.armour.boots;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Speedy extends BaseEnchant {

    public Speedy() {
        super("Speedy","Gain swiftness" , EnchantmentGroup.BOOTS, 3,false);
    }

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, enchantLevel-1));
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }
}
