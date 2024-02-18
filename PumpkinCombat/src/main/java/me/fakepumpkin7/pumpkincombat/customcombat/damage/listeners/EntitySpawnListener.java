package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkincombat.customcombat.damage.CustomDamage;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;

public class EntitySpawnListener implements Listener {


    CustomDamage customDamage;

    HashMap<EntityType,Double> vanillaDamageMap = new HashMap<EntityType, Double>();




    public EntitySpawnListener(CustomDamage customDamage){
        this.customDamage = customDamage;
        initDamageMap();

    }



    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){

        Entity entity = event.getEntity();


        if(vanillaDamageMap.get(entity.getType()) != null){
            CombatUtils.setEntityBaseDamage(entity, vanillaDamageMap.get(entity.getType()));
        }

    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){

        Projectile projectile = event.getEntity();

        if(vanillaDamageMap.get(projectile.getType()) != null){
            CombatUtils.setEntityBaseDamage(projectile, vanillaDamageMap.get(projectile.getType()));
        }
    }



    private void initDamageMap(){
        this.vanillaDamageMap.put(EntityType.CREEPER, 10D);
        this.vanillaDamageMap.put(EntityType.ZOMBIE, 5D);
        this.vanillaDamageMap.put(EntityType.SPIDER, 4D);

        this.vanillaDamageMap.put(EntityType.ARROW, 7D);
    }


}
