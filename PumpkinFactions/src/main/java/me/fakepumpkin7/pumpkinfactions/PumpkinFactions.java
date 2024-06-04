package me.fakepumpkin7.pumpkinfactions;

import me.fakepumpkin7.pumpkinfactions.cmd.CmdFaction;
import me.fakepumpkin7.pumpkinfactions.listener.PlayerAttackPlayerListener;
import me.fakepumpkin7.pumpkinfactions.struct.FactionHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinFactions extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void registerCommands(){
        this.getCommand("faction").setExecutor(new CmdFaction());
    }
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerAttackPlayerListener(), this);
    }

}