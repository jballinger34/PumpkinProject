package me.fakepumpkin7.pumpkinwarzone;

import me.fakepumpkin7.pumpkinwarzone.crates.cmd.ForceWashupCmd;
import me.fakepumpkin7.pumpkinwarzone.crates.cmd.WashupCmd;
import me.fakepumpkin7.pumpkinwarzone.crates.config.ChestConfigHandler;
import me.fakepumpkin7.pumpkinwarzone.crates.listener.ChestListener;
import me.fakepumpkin7.pumpkinwarzone.crates.listener.ChestToolListenerCmd;
import me.fakepumpkin7.pumpkinwarzone.crates.tasks.WashupTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinWarzone extends JavaPlugin {

    private static PumpkinWarzone instance;

    private final ChestToolListenerCmd chestToolListenerCmd = new ChestToolListenerCmd();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        Bukkit.getPluginManager().registerEvents(new ChestListener(), this);
        Bukkit.getPluginManager().registerEvents(chestToolListenerCmd, this);
        this.getCommand("togglechestmode").setExecutor(chestToolListenerCmd);

        ChestConfigHandler.loadChestsFromConfig();

        WashupTask washupTask = new WashupTask();
        Bukkit.getScheduler().runTaskTimer(this, washupTask, 20*60*washupTask.washupCooldown,20*60*washupTask.washupCooldown);
        this.getCommand("washup").setExecutor(new WashupCmd(washupTask));
        this.getCommand("forcewashup").setExecutor(new ForceWashupCmd());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ChestConfigHandler.saveChestsToConfig();
    }

    public static PumpkinWarzone getInstance() {
        return instance;
    }
}
