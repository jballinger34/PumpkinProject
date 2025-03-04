package me.fakepumpkin7.pumpkinframework;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorTask;
import me.fakepumpkin7.pumpkinframework.gui.menu.listener.MenuListener;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatMonitorTask;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class PumpkinFramework extends JavaPlugin {

    @Getter private static PumpkinFramework instance;

    @Getter private static final Random globalRandom = new Random();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerListeners();
        registerTasks();

    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new CombatTagListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

    private void registerTasks() {

        Bukkit.getScheduler().runTaskTimer(this, new ArmorTask(), 5,5);
        Bukkit.getScheduler().runTaskTimer(this, new CombatMonitorTask(), 10,10);
    }
}
