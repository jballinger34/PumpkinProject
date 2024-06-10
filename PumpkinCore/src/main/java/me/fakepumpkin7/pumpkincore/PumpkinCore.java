package me.fakepumpkin7.pumpkincore;

import lombok.Getter;
import me.fakepumpkin7.pumpkincore.hud.ActionBarHandler;
import me.fakepumpkin7.pumpkincore.patches.ClearLag;
import me.fakepumpkin7.pumpkincore.hud.Board;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinCore extends JavaPlugin {

    @Getter
    static PumpkinCore instance;

    //handle reboot
    //tablist
    //format chat
    //permissions?
    // /warp /tpa



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
        Bukkit.getScheduler().runTaskTimer(this, new Board(),0,100);
        Bukkit.getScheduler().runTaskTimer(this, new ActionBarHandler(),0,40);
        Bukkit.getScheduler().runTaskTimer(this, new ClearLag(),0,15*60*20);
    }
}
