package me.fakepumpkin7.pumpkincore;

import lombok.Getter;
import me.fakepumpkin7.pumpkincore.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCore extends JavaPlugin {

    @Getter
    static PumpkinCore instance;

    //clear lag
    //handle reboot
    //scoreboard
    //tablist
    //format chat
    //defence/health over hotbar
    //permissions?



    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        //updates every 5 seconds
        Bukkit.getScheduler().runTaskTimer(this, Board.getInstance(),0,100);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
