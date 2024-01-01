package me.fakepumpkin7.pumpkincombat.customcombat.health;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;

import me.fakepumpkin7.pumpkincombat.customcombat.health.listener.InitCustomHealthListener;

import org.bukkit.Bukkit;


public class CustomHealth {

    PumpkinCombat plugin;


    public CustomHealth(PumpkinCombat plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InitCustomHealthListener(plugin), plugin);

    }



}
