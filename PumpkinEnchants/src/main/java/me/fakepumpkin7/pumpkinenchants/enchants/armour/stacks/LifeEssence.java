package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;

import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class LifeEssence extends BaseEnchant {

    public LifeEssence() {
        super("Life Essence","Chance to heal when attacked" , EnchantmentGroup.ARMOR, 3,true);
    }

    double procChance = 0.05;
    double healAmount = 1;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        double random = Math.random();
        double chance = procChance * enchantLevel;
        if(random < chance){
            CombatUtils.healEntity(user, healAmount);
        }

    }

}
