package me.fakepumpkin7.pumpkincrates;

import me.fakepumpkin7.pumpkincrates.temp.TempCrate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCrates extends JavaPlugin {



    public static String CRATE_ID = "pumpkin-crate";

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(new TempCrate(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
