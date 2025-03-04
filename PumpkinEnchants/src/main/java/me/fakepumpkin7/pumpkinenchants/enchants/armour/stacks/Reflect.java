package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Reflect extends BaseEnchant {

    public Reflect() {
        super("Reflect","Reflects damage" , EnchantmentGroup.ARMOR, 4,true);
    }

    double ReflectionPerLevel = 0.15;

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {

        double random = Math.random();
        double chance = 0.05;

        if (random <= chance) {
            CombatUtils.dealTrueDamage(attacker, event.getFinalDamage()*(ReflectionPerLevel*enchantLevel));
        }
    }
}
