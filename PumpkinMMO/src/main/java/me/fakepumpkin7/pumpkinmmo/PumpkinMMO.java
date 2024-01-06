package me.fakepumpkin7.pumpkinmmo;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinMMO extends JavaPlugin {

    //TODO
    // PLAYER HAS DIFFERENT SKILLS
    // COMBAT
    // FARMING
    // MINING
    // FISHING
    //
    // WOODCUTTING/FORAGING
    // ALCHEMY
    //

    // SKILLS STORED ON DISK/DB,
    // WHEN PLAYER LOADS INTO SERVER
    // PLAYERS SKILLS LOADED INTO RAM
    // WHERE THEY CAN BE LEVELLED UP,
    // AND THEY GAIN THE EFFECTS OF THE SKILLS


    //IMPL
    // onenable saves

    @Getter
    static PumpkinMMO instance;
    SkillHandler skillHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        // Plugin startup logic
        skillHandler = new SkillHandler();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        skillHandler.saveToConfig();
    }
}
