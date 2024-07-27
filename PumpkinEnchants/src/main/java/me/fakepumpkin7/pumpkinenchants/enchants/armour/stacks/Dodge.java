package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;

public class Dodge extends BaseEnchant {

    public Dodge() {
        super("Dodge","Chance to dodge attacks" , EnchantmentGroup.ARMOR, 5,true);
    }

    double procChance = 0.008;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        double random = Math.random();
        double chance = procChance * enchantLevel;
        if(random < chance){
            event.setCancelled(true);
        }

    }

}
