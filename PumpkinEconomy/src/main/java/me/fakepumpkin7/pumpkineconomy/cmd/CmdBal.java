package me.fakepumpkin7.pumpkineconomy.cmd;

import me.fakepumpkin7.pumpkineconomy.Bank;
import me.fakepumpkin7.pumpkineconomy.config.EconomyConfigHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdBal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("Only a player can use this command.");
            return false;
        } 
        Player player = (Player) commandSender;
        if(strings.length == 0){
            double balance = Bank.getInstance().getBalance(player.getUniqueId());
            ChatUtils.notify(player, "Your balance is: " + balance);
            return true;
        }
        if(strings.length == 1){

            String name = strings[0];
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

            if(offlinePlayer == null){
                ChatUtils.info(player, "Cannot find a player with this name.");
                return true;
            }

            UUID uuid = offlinePlayer.getUniqueId();
            if(Bank.getInstance().getBalance(uuid) != null){
                double balance = Bank.getInstance().getBalance(uuid);
                ChatUtils.notify(player, name + "'s balance is: " + balance);
                return true;
            }
            //not in cache, check config
            Double bal = EconomyConfigHandler.getBalanceFromConfig(uuid);
            if(bal != null){
                ChatUtils.notify(player, name + "'s balance is: " + bal);
                return true;
            }
            ChatUtils.info(player, "Cannot find a player with this name.");
            return true;


        }
        return false;
    }
}
