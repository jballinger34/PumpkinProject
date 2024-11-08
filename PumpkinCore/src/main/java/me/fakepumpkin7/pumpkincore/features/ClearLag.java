package me.fakepumpkin7.pumpkincore.features;

import me.fakepumpkin7.pumpkincore.PumpkinCore;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


public class ClearLag implements Runnable{


    @Override
    public void run() {
        ChatUtils.broadcast(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "[ClearLag] " + ChatColor.WHITE + "Removing dropped items and entities in 30 seconds");

        Bukkit.getScheduler().runTaskLater(PumpkinCore.getInstance(), () -> {
            int count = 0;
            for(World world : Bukkit.getWorlds()){
                for(Entity entity : world.getEntities()){
                    if(entity instanceof Player) continue;
                    if(entity.hasMetadata("pumpkin-no-clearlag")) continue;
                    entity.remove();
                    count++;
                }
            }
            ChatUtils.broadcast(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "[ClearLag] " + ChatColor.WHITE + "Removed " + count + " dropped items and entities.");
        }, 30*20);

    }
}
