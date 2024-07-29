package me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee;

import com.rit.sucy.EnchantPlugin;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Hellish extends BaseEnchant {
    public Hellish() {
        super("Hellish","Fire damage that goes through fire res", EnchantmentGroup.MELEE_WEAPONS,5);
    }

    static String metadataTag = "hellish-tick";
    int secondsPerLevel = 1;
    double dps = 1.0;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        if (target.hasMetadata(metadataTag) && target.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            return;
        }
        int activeSeconds = secondsPerLevel*enchantLevel;
        int activeTicks = activeSeconds*20;
        long activeMS = activeSeconds*1000L;

        target.setFireTicks(target.getFireTicks() + activeTicks);

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(PumpkinEnchants.getInstance(), () -> CombatUtils.dealTrueDamage(target, dps),0,20);
        Bukkit.getScheduler().runTaskLater(PumpkinEnchants.getInstance(), () -> task.cancel(), activeTicks);


        target.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), System.currentTimeMillis() + activeMS));

    }

}