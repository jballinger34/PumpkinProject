package me.fakepumpkin7.pumpkinarmour;

import me.fakepumpkin7.pumpkinarmour.cmd.GiveArmourSetCommand;
import me.fakepumpkin7.pumpkinarmour.impl.vanilla.VanillaArmour;
import me.fakepumpkin7.pumpkinarmour.listener.ArmourChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinArmour extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(new ArmourChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new VanillaArmour(), this);

        this.getCommand("givearmourset").setExecutor(new GiveArmourSetCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
