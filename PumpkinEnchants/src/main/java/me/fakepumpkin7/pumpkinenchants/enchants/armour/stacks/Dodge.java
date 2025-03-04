package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Dodge extends BaseEnchant {

    public Dodge() {
        super("Dodge","Chance to dodge attacks" , EnchantmentGroup.ARMOR, 5,true);
    }

    double procChance = 0.008;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        double random = Math.random();
        double chance = procChance * enchantLevel;
        if(random < chance){
            event.setCancelled(true);
        }

    }

}
