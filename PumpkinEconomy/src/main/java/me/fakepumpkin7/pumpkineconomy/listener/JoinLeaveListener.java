package me.fakepumpkin7.pumpkineconomy.listener;

import me.fakepumpkin7.pumpkineconomy.PumpkinEconomy;
import me.fakepumpkin7.pumpkineconomy.config.EconomyConfigHandler;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinLeaveListener implements Listener {

    //gets a players balance from config when they join
    //adds them to memory

    //saves a players balance to config when they leave
    //removes them from memory

    //this means

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        Double balance = EconomyConfigHandler.getBalanceFromConfig(uuid);
        if(balance == null){
            balance = PumpkinEconomy.initialBalance;
        }
        Bank.setBalance(uuid,balance);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        EconomyConfigHandler.updateBalanceInConfig(uuid);
        Bank.removeEntry(uuid);
    }



}
