package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Adrenaline extends BaseEnchant {

    public Adrenaline() {
        super("Adrenaline","Deal more damage at low health" , EnchantmentGroup.ARMOR, 5,false);
    }

    double multiplierPerLevel = 0.05;


    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        //if health under 20% of max, proc
        if (user.getHealth() < (user.getMaxHealth()/5)){
            //could add a random variable so this doesnt proc 100%.


            //work out damage stat to add, add it to player
            event.setDamage(event.getDamage()*(1+(multiplierPerLevel*enchantLevel)));


        }
    }
}
