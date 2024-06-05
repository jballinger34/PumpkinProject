package me.fakepumpkin7.pumpkinfactions;

import lombok.Getter;
import me.fakepumpkin7.pumpkinfactions.cmd.CmdFaction;
import me.fakepumpkin7.pumpkinfactions.listener.PlayerAttackPlayerListener;
import me.fakepumpkin7.pumpkinfactions.struct.FactionHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinFactions extends JavaPlugin {

    @Getter
    static PumpkinFactions instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
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
