package me.fakepumpkin7.pumpkincrates;

import me.fakepumpkin7.pumpkincrates.cmd.CmdGiveCrate;
import me.fakepumpkin7.pumpkincrates.listeners.CrateListener;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.economy.EconomyAPI;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCrates extends JavaPlugin {



    public static String CRATE_ID = "pumpkin-crate";

    @Override
    public void onEnable() {
        // Plugin startup logic

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new CrateListener(), this);
    }
    private void registerCommands(){
        this.getCommand("givecrate").setExecutor(new CmdGiveCrate());
    }
}
