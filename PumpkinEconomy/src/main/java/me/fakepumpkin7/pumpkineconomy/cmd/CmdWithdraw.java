package me.fakepumpkin7.pumpkineconomy.cmd;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import me.fakepumpkin7.pumpkinframework.items.ItemUtil;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class CmdWithdraw implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //need a method in framework to get money note, maybe in itembuilder.
        if(!(commandSender instanceof Player)){
            System.out.println("Only a player can use this command.");
            return false;
        }
        Player player = (Player) commandSender;
        if(strings.length != 1){
            ChatUtils.info(player, "Usage /withdraw <amount>");
            return true;
        }
        Double amount;
        try{
            amount = Double.parseDouble(strings[0]);
        } catch (Exception exception){
            ChatUtils.info(player, "Invalid amount.");
            return true;
        }
        if(amount < 1000){
            ChatUtils.info(player, "Invalid amount, withdraw at least 1000.");
            return true;
        }
        if(!Bank.hasFunds(player.getUniqueId(), amount)){
            ChatUtils.info(player, "You do not have adequate funds.");
            return true;
        }

        ChatUtils.notify(player, "Successfully withdrew " + amount);
        Bank.addBalance(player.getUniqueId(), -amount);
        ItemStack moneyNote = Bank.createMoneyNote(player.getName(), amount);
        PlayerUtils.addItems(player, moneyNote);







        return true;
    }
}
