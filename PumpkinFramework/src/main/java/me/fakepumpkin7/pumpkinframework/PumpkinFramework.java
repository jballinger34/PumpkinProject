package me.fakepumpkin7.pumpkinframework;

import me.fakepumpkin7.pumpkinframework.armor.events.ArmorTask;
import me.fakepumpkin7.pumpkinframework.gui.menu.listener.MenuListener;
import me.fakepumpkin7.pumpkinframework.items.interactive.InteractiveItemCommand;
import me.fakepumpkin7.pumpkinframework.items.interactive.InteractiveItemUtils;
import me.fakepumpkin7.pumpkinframework.items.interactive.testimpl.MagicBowl;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatMonitorTask;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class PumpkinFramework extends JavaPlugin {

    private static PumpkinFramework instance;

    private static final Random globalRandom = new Random();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerListeners();
        registerTasks();
        registerCommands();

        InteractiveItemUtils.register(MagicBowl.id, new MagicBowl());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new CombatTagListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractiveItemUtils(), this);
    }

    private void registerTasks() {

        Bukkit.getScheduler().runTaskTimer(this, new ArmorTask(), 5,5);
        Bukkit.getScheduler().runTaskTimer(this, new CombatMonitorTask(), 10,10);
    }
    private void registerCommands(){
        this.getCommand("giveii").setExecutor(new InteractiveItemCommand());
    }

    public static PumpkinFramework getInstance() {
        return instance;
    }
}
