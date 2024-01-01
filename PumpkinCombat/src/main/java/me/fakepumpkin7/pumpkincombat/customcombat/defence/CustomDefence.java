package me.fakepumpkin7.pumpkincombat.customcombat.defence;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.InitCustomDefenceListener;
import org.bukkit.Bukkit;

public class CustomDefence {
    public CustomDefence(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new InitCustomDefenceListener(plugin), plugin);


    }
}
