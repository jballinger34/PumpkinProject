package me.fakepumpkin7.pumpkincombat.customcombat.defence;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.ArmorChangeListener;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.InitCustomDefenceListener;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;


public class CustomDefence {

    PumpkinCombat plugin;
    public CustomDefence(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new InitCustomDefenceListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new ArmorChangeListener(this), plugin);

        this.plugin = plugin;
    }

    public double getEntityDefence(Entity entity){
        double defence = 0;
        if(entity.hasMetadata("pumpkin-custom-defence")){
            defence = entity.getMetadata("pumpkin-custom-defence").get(0).asDouble();
        }
        return defence;
    }

    public void setEntityDefence(Entity entity, double toSet){
        toSet = Math.max(0,toSet);
        entity.setMetadata("pumpkin-custom-defence", new FixedMetadataValue(plugin, toSet));
    }

    public void addEntityDefence(Entity entity, double toAdd){
        double current = getEntityDefence(entity);
        double newVal = current + toAdd;

        setEntityDefence(entity, newVal);
    }

    public double getItemDefence(ItemStack itemStack){
        double defence = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-defence" )){
            defence = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-defence");
        }
        return defence;
    }

    public void setItemDefence(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-defence",toSet);
    }
    public void addItemDefence(ItemStack itemStack, double toAdd){
        double current = getItemDefence(itemStack);
        double newVal = current + toAdd;

        setItemDefence(itemStack, newVal);
    }




}

