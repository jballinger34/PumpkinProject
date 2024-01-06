package me.fakepumpkin7.pumpkincombat.customcombat.health;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;

import me.fakepumpkin7.pumpkincombat.customcombat.health.listener.InitCustomHealthListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;


public class CustomHealth {

    PumpkinCombat plugin;


    public CustomHealth(PumpkinCombat plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InitCustomHealthListener(plugin), plugin);

    }

    public double getPlayerHealth(Player player){
        double health = 20;

        if(player.hasMetadata("pumpkin-custom-health")){
            health = player.getMetadata("pumpkin-custom-health").get(0).asDouble();
        }

        return health;
    }



}
