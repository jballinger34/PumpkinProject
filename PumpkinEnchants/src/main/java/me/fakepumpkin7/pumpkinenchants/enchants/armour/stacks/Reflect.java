package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Reflect extends BaseEnchant {

    public Reflect() {
        super("Reflect","Reflects damage" , EnchantmentGroup.ARMOR, 4,true);
    }

    double ReflectionPerLevel = 0.15;

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {

        double random = Math.random();
        double chance = 0.05;

        if (random <= chance) {
            CombatUtils.dealTrueDamage(attacker, event.workOutDamageAfterDefence()*(ReflectionPerLevel*enchantLevel));
        }
    }
}
