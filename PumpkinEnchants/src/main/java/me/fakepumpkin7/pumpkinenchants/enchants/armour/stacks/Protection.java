package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Protection extends BaseEnchant {

    public Protection() {
        super("Protection","Reduce damage taken" , EnchantmentGroup.ARMOR, 5,true);
    }


    double multiplierPerLevel = -0.05;


    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        event.setDamage(event.getDamage()*(1+(multiplierPerLevel*enchantLevel)));
    }

}
