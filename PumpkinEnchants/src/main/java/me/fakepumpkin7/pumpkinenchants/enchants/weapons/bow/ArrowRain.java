package me.fakepumpkin7.pumpkinenchants.enchants.weapons.bow;

import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantmentGroup;
import me.fakepumpkin7.pumpkinenchants.PumpkinEnchants;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Random;

import static org.bukkit.Material.AIR;


public class ArrowRain extends BaseEnchant implements Listener {

    String metadataTag = "arrow-rain-arrow";
    public ArrowRain() {
        super("Arrow Rain","Rain arrows from the sky.", EnchantmentGroup.BOW, 5);

        Bukkit.getPluginManager().registerEvents(this, PumpkinEnchants.getInstance());
    }

    double procChancePerLevel = 0.02;

    @Override
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
        double random = Math.random();
        double chance = procChancePerLevel * enchantLevel;

        if(random < chance) {
            // Spawn a bunch of arrows a few Y up, random x/z locations near target.
            int arrow_count = (int) Math.ceil((double) enchantLevel * 1.5D) + 3;
            while (arrow_count > 0) {
                arrow_count--;
                Arrow arrow = target.getWorld().spawnArrow(getRandomLocationNearby(target.getLocation(), 6, 20), new Vector(0, -1, 0), 1.25F, 1F);
                arrow.setShooter(user);

                arrow.setMetadata(metadataTag, new FixedMetadataValue(PumpkinEnchants.getInstance(), 1));

                if (enchantLevel >= 3) {
                    arrow.setCritical(true);
                }

                if (enchantLevel == 5) {
                    arrow.setFireTicks(600);
                }
            }
        }
    }

    public static Location getRandomLocationNearby(Location l, int max_dist, int y_boost) {
        int min_distance = 0;
        Random rand = new Random();
        Location rand_loc = l.clone();

        // Add our offset.
        rand_loc.add((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max_dist - min_distance) + min_distance),
                0, // Y-coord
                (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max_dist - min_distance) + min_distance));

        // Center of block.
        rand_loc.add(0.5, y_boost, 0.5);

        while ((rand_loc.getBlock().getType() != AIR || rand_loc.getBlock().getLocation().subtract(0, 1, 0).getBlock().getType() != AIR) && rand_loc.getY() < 255) {
            rand_loc = rand_loc.add(0, 1, 0);
        }

        return rand_loc;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata(metadataTag)) {
            //Remove arrows after one tick so they still deal their correct damage
            Bukkit.getScheduler().runTaskLater(PumpkinEnchants.getInstance(), () -> event.getEntity().remove() , 20);
        }
    }

}
