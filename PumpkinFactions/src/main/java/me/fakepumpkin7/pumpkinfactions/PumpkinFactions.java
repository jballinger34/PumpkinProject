package me.fakepumpkin7.pumpkinfactions;

import lombok.Getter;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.CmdFaction;
import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
import me.fakepumpkin7.pumpkinfactions.listener.*;
import me.fakepumpkin7.pumpkinframework.factions.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinFactions extends JavaPlugin {

    @Getter
    static PumpkinFactions instance;

    private FactionHandler factionHandler = FactionHandler.getInstance();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        registerCommands();
        registerListeners();


        FactionManager.setFactionAPI(factionHandler);


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
        if(factionHandler.getFactionFromName("WarZone") == null){
            factionHandler.createNewFaction(null,"WarZone");
        }
    }
}
