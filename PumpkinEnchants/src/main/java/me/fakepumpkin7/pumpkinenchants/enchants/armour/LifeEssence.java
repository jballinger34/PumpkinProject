package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Random;

public class LifeEssence extends BaseEnchant {

    public LifeEssence() {
        super("Life Essence","Chance to gain health when attacked" , EnchantmentGroup.ARMOR, 3,true);
    }

    double procChance = 0.05;
    double healAmount = 1;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        System.out.println("Hit So Ran");
        double random = Math.random();
        double chance = procChance * enchantLevel;
        if(random < chance){
            CombatUtils.healEntity(user, healAmount);
        }

    }

}
