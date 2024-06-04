package me.fakepumpkin7.pumpkincombat.customcombat.health.listener;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InitCustomHealthListener implements Listener {


    @EventHandler
    public void initCHonEntitySpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        double defaultHealth = 20;

        if(entity instanceof LivingEntity){
            defaultHealth = ((LivingEntity) entity).getMaxHealth();
        }
        CombatUtils.setEntityMaxHealth(entity, defaultHealth);
    }

}
