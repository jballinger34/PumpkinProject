package me.fakepumpkin7.pumpkineconomy;

import lombok.Getter;
import me.fakepumpkin7.pumpkineconomy.cmd.CmdBal;
import me.fakepumpkin7.pumpkineconomy.cmd.CmdBalTop;
import me.fakepumpkin7.pumpkineconomy.cmd.CmdPay;
import me.fakepumpkin7.pumpkineconomy.cmd.CmdWithdraw;
import me.fakepumpkin7.pumpkineconomy.config.EconomyConfigHandler;
import me.fakepumpkin7.pumpkineconomy.listener.JoinLeaveListener;
import me.fakepumpkin7.pumpkineconomy.listener.MoneyNoteListener;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PumpkinEconomy extends JavaPlugin {

    @Getter
    static PumpkinEconomy instance;

    public static final double initialBalance = 1000;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;


        EconomyManager.setEconomyAPI(Bank.getInstance());

        registerListeners();
        registerCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        EconomyConfigHandler.saveAllPlayers();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(),this);
        Bukkit.getPluginManager().registerEvents(new MoneyNoteListener(),this);
    }
    private void registerCommands(){
        this.getCommand("balance").setExecutor(new CmdBal());
        this.getCommand("pay").setExecutor(new CmdPay());
        this.getCommand("withdraw").setExecutor(new CmdWithdraw());
        this.getCommand("balancetop").setExecutor(new CmdBalTop());
    }
}
