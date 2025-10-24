package me.fakepumpkin7.pumpkinmmo;

import me.fakepumpkin7.pumpkinmmo.cmd.SkillCommand;
import me.fakepumpkin7.pumpkinmmo.listener.SkillExpGainListener;
import org.bukkit.Bukkit;
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

    static PumpkinMMO instance;
    SkillHandler skillHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        skillHandler = new SkillHandler();

        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        skillHandler.saveToConfig();
    }

    private void registerCommands(){
        this.getCommand("skill").setExecutor(new SkillCommand(this));
    }
    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new SkillExpGainListener(skillHandler), this);
    }

    public static PumpkinMMO getInstance() {
        return instance;
    }

    public SkillHandler getSkillHandler() {
        return skillHandler;
    }
}
