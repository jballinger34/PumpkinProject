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
        Bukkit.getPluginManager().registerEvents(new InitCustomHealthListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new HealthArmorChangeListener(this), plugin);

    }


}
