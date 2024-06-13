package me.fakepumpkin7.pumpkinframework.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    public static void broadcast(String message){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(message);
        }
    }

    public static void error(Player player, String message){
        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) "+ ChatColor.RED + message);
    }
    public static void warn(Player player, String message){
        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) "+ ChatColor.WHITE + message);
    }
    public static void info(Player player, String message){
        player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) "+ ChatColor.WHITE + message);
    }
    public static void notify(Player player, String message){
        player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) "+ ChatColor.WHITE + message);
    }
    public static void success(Player player, String message){
        player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) "+ ChatColor.GREEN + message);
    }
    public static void sendDivider(Player player, String colour){
        player.sendMessage(colour + "------------------------------------------------------");
    }
    public static String colour(String message){
        return message != null && !message.isEmpty() ? ChatColor.translateAlternateColorCodes('&', message) : message;
    }
}
