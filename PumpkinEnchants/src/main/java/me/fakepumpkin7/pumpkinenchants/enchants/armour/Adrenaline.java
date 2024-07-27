package me.fakepumpkin7.pumpkinenchants.enchants.armour;

import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

public class Adrenaline extends BaseEnchant {

    String metadataTag = "adrenaline-proc";
    public Adrenaline() {
        super("Adrenaline","Deal more damage at low health" , EnchantmentGroup.ARMOR, 5,false);
    }

    double damagePerLevel = 10;
    double durationTicks = 20;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        //if currently active, dont do anything
        if (user.hasMetadata(metadataTag) && user.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            return;
        }

        //if health under 20% of max, proc
        if (CombatUtils.getEntityCustomHealth(user) < (CombatUtils.getEntityMaxHealth(user)/5)){
            //work out damage stat to add, add it to player
            double baseDamageToAdd = enchantLevel*damagePerLevel;
            CombatUtils.addEntityBaseDamage(user,baseDamageToAdd);

            //work out time this will be added for
            double activeTimeMS = 1000*durationTicks/20;

            //add metadata, saying that this enchant is currently active
            user.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), System.currentTimeMillis() + activeTimeMS));

            //schedule to remove the added damage stat after duration.
            Bukkit.getScheduler().runTaskLater(PumpkinEnchants.getInstance(), () -> CombatUtils.addEntityBaseDamage(user, -baseDamageToAdd), (int) durationTicks);


        }
    }
}
