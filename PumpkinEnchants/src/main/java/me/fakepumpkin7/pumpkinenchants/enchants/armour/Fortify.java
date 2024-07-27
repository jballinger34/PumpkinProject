package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Fortify extends BaseEnchant {

    public Fortify() {
        super("Fortify","Gain defence at low health" , EnchantmentGroup.ARMOR, 5,true);
    }

    double DefencePerLevel = 5;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if (CombatUtils.getEntityCustomHealth(user) < (CombatUtils.getEntityMaxHealth(user)/5)){
            CombatUtils.addEntityDefence(user,(enchantLevel*DefencePerLevel));
            //Add BUCKET SCHEDULE THING???
        }
    }

}
