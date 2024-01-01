package me.fakepumpkin7.pumpkinframework;

import lombok.Getter;
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

    }

    private void registerTasks() {

    }
}
