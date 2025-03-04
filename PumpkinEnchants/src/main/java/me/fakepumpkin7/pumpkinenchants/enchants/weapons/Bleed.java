package me.fakepumpkin7.pumpkinenchants.enchants.weapons;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

public class Bleed extends BaseEnchant {

    public Bleed() {
        super("Bleed","Deal tick damage to the enemy", EnchantmentGroup.MELEE_WEAPONS,4);
    }

    static String metadataTag = "bleed-tick";
    int secondsPerLevel = 2;
    double dps = 1.0;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target.hasMetadata(metadataTag) && target.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            return;
        }
        int activeSeconds = secondsPerLevel*enchantLevel;
        int activeTicks = activeSeconds*20;
        long activeMS = activeSeconds*1000L;


        BukkitTask task = Bukkit.getScheduler().runTaskTimer(PumpkinEnchants.getInstance(), () -> CombatUtils.dealTrueDamage(target, dps),0,20);
        Bukkit.getScheduler().runTaskLater(PumpkinEnchants.getInstance(), () -> task.cancel(), activeTicks);


        target.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), System.currentTimeMillis() + activeMS));
    }
}
