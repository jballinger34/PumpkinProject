package me.fakepumpkin7.pumpkinmobs;

import me.fakepumpkin7.pumpkinmobs.cmd.SpawnTestCmd;
import me.fakepumpkin7.pumpkinmobs.mobs.HealthInNameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinMobs extends JavaPlugin {



    DropHandler dropHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        dropHandler = new DropHandler(this);
        this.getCommand("spawntest").setExecutor(new SpawnTestCmd());
        Bukkit.getPluginManager().registerEvents(new HealthInNameListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
