package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireResistance extends BaseEnchant {

    public FireResistance() {
        super("Fire Resistance","Negates fire damage" , EnchantmentGroup.LEGGINGS, 1,false);
    }

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,999999999,0));
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    }
}
