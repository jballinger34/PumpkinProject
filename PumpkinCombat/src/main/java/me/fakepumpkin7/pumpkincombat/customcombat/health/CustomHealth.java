package me.fakepumpkin7.pumpkincombat.customcombat.health;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;

import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.DefenceArmorChangeListener;
import me.fakepumpkin7.pumpkincombat.customcombat.health.listener.HealthArmorChangeListener;
import me.fakepumpkin7.pumpkincombat.customcombat.health.listener.InitCustomHealthListener;

import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;


public class CustomHealth {

    PumpkinCombat plugin;


    public CustomHealth(PumpkinCombat plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InitCustomHealthListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new HealthArmorChangeListener(this), plugin);

    }

    public double getEntityHealth(Entity entity){
        double health = 20;

        if(entity.hasMetadata("pumpkin-custom-health")){
            health = entity.getMetadata("pumpkin-custom-health").get(0).asDouble();
        }

        return health;
    }

    public void setEntityHealth(Entity entity, double toSet){
        toSet = Math.max(0,toSet);
        entity.setMetadata("pumpkin-custom-health", new FixedMetadataValue(plugin, toSet));
    }
    public void addEntityHealth(Entity entity, double toAdd){
        double current = getEntityHealth(entity);

        double newVal = current + toAdd;

        setEntityHealth(entity, newVal);
    }

    public double getItemHealth(ItemStack itemStack){
        double health = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-health" )){
            health = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-health");
        }
        return health;
    }

    public void setItemHealth(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-health",toSet);
    }
    public void addItemHealth(ItemStack itemStack, double toAdd){
        double current = getItemHealth(itemStack);
        double newVal = current + toAdd;

        setItemHealth(itemStack, newVal);
    }

}
