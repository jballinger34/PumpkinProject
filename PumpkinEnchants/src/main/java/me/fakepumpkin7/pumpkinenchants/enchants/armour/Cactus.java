package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Cactus extends BaseEnchant {

    public Cactus() {
        super("Cactus", "Deals damage back when hit", EnchantmentGroup.ARMOR, 1, false);
    }


    double damagePerLevel = 1;
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {

        double random = Math.random();
        double chance = 0.25;

        if (random <= chance) {
            CombatUtils.dealTrueDamage(attacker, damagePerLevel*enchantLevel);
        }
    }
}
