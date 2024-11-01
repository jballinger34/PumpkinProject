package me.fakepumpkin7.pumpkinfactions;

import lombok.Getter;
import me.fakepumpkin7.pumpkinfactions.cmd.CmdFaction;
import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
import me.fakepumpkin7.pumpkinfactions.listener.*;
import me.fakepumpkin7.pumpkinframework.factions.Faction;
import me.fakepumpkin7.pumpkinframework.factions.FactionHandler;
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



        FactionConfigHandler.loadFromConfig();
        createWarZoneFac();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        FactionConfigHandler.saveAllToConfig();
    }
    private void registerCommands(){
        this.getCommand("faction").setExecutor(new CmdFaction());
    }
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerAttackPlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClaimChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new FactionMemberChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new FactionBlockProtectListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEnterClaimListener(), this);

    }

    private void createWarZoneFac(){
        if(FactionHandler.getFactionFromName("WarZone") == null){
            FactionHandler.createNewFaction(null,"WarZone");
        }
    }
}
