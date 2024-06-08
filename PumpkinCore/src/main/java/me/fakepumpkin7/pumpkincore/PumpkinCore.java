package me.fakepumpkin7.pumpkincore;

import lombok.Getter;
import me.fakepumpkin7.pumpkincore.patches.ClearLag;
import me.fakepumpkin7.pumpkincore.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCore extends JavaPlugin {

    @Getter
    static PumpkinCore instance;

    //clear lag
    //handle reboot
    //tablist
    //format chat
    //defence/health over hotbar
    //permissions?



    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerTasks();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerTasks(){
        Bukkit.getScheduler().runTaskTimer(this, Board.getInstance(),0,100);
        Bukkit.getScheduler().runTaskTimer(this, new ClearLag(),0,30*20);
    }
}
