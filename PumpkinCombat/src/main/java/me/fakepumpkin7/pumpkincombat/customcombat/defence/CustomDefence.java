package me.fakepumpkin7.pumpkincombat.customcombat.defence;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.DefenceArmorChangeListener;
import org.bukkit.Bukkit;


public class CustomDefence {

    PumpkinCombat plugin;
    public CustomDefence(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new DefenceArmorChangeListener(this), plugin);

        this.plugin = plugin;
    }





}

