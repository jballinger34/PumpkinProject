package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Marksman extends BaseEnchant {
    public Marksman() {
        super("Marksman","Deal increased damage with a bow", EnchantmentGroup.ARMOR,4, true);
    }
    double multiplierPerLevel = 0.2;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if(!(user instanceof Player)) return;
        Player player = (Player) user;

        if(player.getItemInHand().getType().equals(Material.BOW)){
            double multiplier = 1 + multiplierPerLevel*enchantLevel;

            event.setDamage(event.getDamage()*multiplier);
        }
    }
}

