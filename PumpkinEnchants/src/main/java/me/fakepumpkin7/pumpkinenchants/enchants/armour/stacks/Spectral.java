package me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spectral extends BaseEnchant {

    String metadataTag = "spectral-active";

    public Spectral() {
        super("Spectral","Chance to go invisible and invincible" , EnchantmentGroup.ARMOR, 7,true);
    }

    double procChance = 0.01;
    int durationTicksPerLevel = 10;

    @Override
    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
        if (user.hasMetadata(metadataTag) && user.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            event.setCancelled(true);
            return;
        }


        double random = Math.random();
        double chance = procChance;
        if (random < chance) {
            //convert the ticks to ms by multiply by 1000/20
            int activeTimeTicks = durationTicksPerLevel*enchantLevel;
            int activeTimeMS = 1000*(activeTimeTicks)/20;

            user.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, activeTimeTicks, 0));
            user.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), System.currentTimeMillis() + activeTimeMS));

        }
    }
}