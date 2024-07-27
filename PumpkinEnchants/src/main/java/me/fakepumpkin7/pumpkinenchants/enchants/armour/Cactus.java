package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;

public class Cactus extends BaseEnchant {

    public Cactus() {
        super("Cactus", "Deals damage back when hit", EnchantmentGroup.ARMOR, 1, true);
    }


    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {

        double random = Math.random();
        double chance = 0.25;

        if (random <= chance) {
            CombatUtils.dealTrueDamage(attacker, 1);
        }
    }
}
