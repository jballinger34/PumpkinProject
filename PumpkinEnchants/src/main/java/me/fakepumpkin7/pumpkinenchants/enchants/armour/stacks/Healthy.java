package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;


import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Healthy extends BaseEnchant {

    public Healthy() {
        super("Healthy","Gain health boost" , EnchantmentGroup.ARMOR, 3,true);
    }

    @Override
    public void applyEquipEffect(Player player, int enchantLevel) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999999, enchantLevel-1));
    }

    @Override
    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
    }

}
