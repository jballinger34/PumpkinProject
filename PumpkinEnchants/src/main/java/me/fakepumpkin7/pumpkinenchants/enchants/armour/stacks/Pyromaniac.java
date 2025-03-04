package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Pyromaniac extends BaseEnchant {
    public Pyromaniac() {
        super("Pyromaniac","Chance to heal from fire ticks" , EnchantmentGroup.ARMOR, 5,true);
    }

    double procChance = 0.1;
    double healAmount = 1;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if(event.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) return;
        double random = Math.random();
        double chance = procChance * enchantLevel;
        if(random < chance){
            CombatUtils.healEntity(user, healAmount);
        }
    }
}
