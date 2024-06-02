package me.fakepumpkin7.pumpkincombat.customcombat.damage;


import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.*;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class CustomDamage {

    PumpkinCombat plugin;

    public CustomDamage(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new DamageListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InitCustomDamageListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new DamageArmorChangeListener(this), plugin);

        this.plugin = plugin;

    }



}
