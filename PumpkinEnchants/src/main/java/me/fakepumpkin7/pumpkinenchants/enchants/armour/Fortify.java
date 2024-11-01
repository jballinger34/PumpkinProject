package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Fortify extends BaseEnchant {

    //Check in Adrenaline for rundown on how this works


    String metadataTag = "fortify-proc";

    public Fortify() {
        super("Fortify","Gain defence at low health" , EnchantmentGroup.ARMOR, 5,false);
    }

    double defencePerLevel = 5;

    double durationTicks = 20;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {

        if (user.hasMetadata(metadataTag) && user.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            return;
        }

        if (CombatUtils.getEntityCustomHealth(user) < (CombatUtils.getEntityMaxHealth(user)/5)){
            double defenceToAdd = enchantLevel*defencePerLevel;
            CombatUtils.addEntityDefence(user,defenceToAdd);


            double activeTimeMS = 1000*durationTicks/20;
            user.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), System.currentTimeMillis() + activeTimeMS));
            Bukkit.getScheduler().runTaskLater(PumpkinEnchants.getInstance(), () -> CombatUtils.addEntityBaseDamage(user, -defenceToAdd), (int) durationTicks);
        }
    }

}
