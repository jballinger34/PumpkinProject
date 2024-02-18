package me.fakepumpkin7.pumpkincombat.customcombat.defence;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.DefenceArmorChangeListener;
import me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners.InitCustomDefenceListener;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;


public class CustomDefence {

    PumpkinCombat plugin;
    public CustomDefence(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new InitCustomDefenceListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new DefenceArmorChangeListener(this), plugin);

        this.plugin = plugin;
    }





}

