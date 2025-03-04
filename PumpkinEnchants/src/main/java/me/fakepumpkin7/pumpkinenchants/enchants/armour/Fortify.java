package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Fortify extends BaseEnchant {

    public Fortify() {
        super("Fortify","Take less damage when at low health" , EnchantmentGroup.ARMOR, 5,false);
    }

    double multiplierPerLevel = -0.05;


    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, EntityDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if (user.getHealth() < (user.getMaxHealth()/5)){
            event.setDamage(event.getDamage()*(1+(multiplierPerLevel*enchantLevel)));
        }
    }

}
