package me.fakepumpkin7.pumpkinenchants.enchants.armour.helmet;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Clarity extends BaseEnchant {

    public Clarity() {
        super("Clarity","Gain night vision" , EnchantmentGroup.HELMET, 1,false);
    }

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,999999999,0));
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
