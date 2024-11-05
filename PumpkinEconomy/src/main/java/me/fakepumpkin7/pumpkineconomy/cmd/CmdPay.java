package me.fakepumpkin7.pumpkineconomy.cmd;

import me.fakepumpkin7.pumpkineconomy.Bank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdPay implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("Only a player can use this command.");
            return false;
        }
        Player player = (Player) commandSender;
        if(strings.length != 2){
            ChatUtils.info(player, "Usage /pay <player> <amount>");
            return true;
        }
        String name = strings[0];
        String amountStr = strings[1];

        if(Bukkit.getOfflinePlayer(name) == null || !Bukkit.getOfflinePlayer(name).isOnline()){
            ChatUtils.info(player, "Cannot find player.");
            return true;
        }
        Player receiver = Bukkit.getOfflinePlayer(name).getPlayer();
        UUID sendTo = receiver.getUniqueId();
        UUID sender = player.getUniqueId();
        if(sender.equals(sendTo)){
            ChatUtils.info(player, "Cannot send money to yourself.");
            return true;
        }


        Double amount;
        try{
            amount = Double.parseDouble(amountStr);
        } catch (Exception exception){
            ChatUtils.info(player, "Invalid amount.");
            return true;
        }
        if(amount <= 0){
            ChatUtils.info(player, "Invalid amount.");
            return true;
        }

        if(!Bank.getInstance().hasFunds(sender, amount)){
            ChatUtils.info(player, "You do not have adequate funds.");
            return true;
        }

        Bank.getInstance().addBalance(sender,-amount);
        Bank.getInstance().addBalance(sendTo,amount);

        ChatUtils.notify(player, "Sent "+ amount + " to " + receiver.getName());
        ChatUtils.notify(receiver, "Recieved "+ amount + " from " + player.getName());

        return true;
    }
}