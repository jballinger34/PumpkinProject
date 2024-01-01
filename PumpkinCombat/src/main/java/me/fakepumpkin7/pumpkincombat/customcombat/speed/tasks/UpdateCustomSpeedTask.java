package me.fakepumpkin7.pumpkincombat.customcombat.speed.tasks;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.CustomSpeed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class UpdateCustomSpeedTask extends BukkitRunnable {

    PumpkinCombat plugin;
    public UpdateCustomSpeedTask(PumpkinCombat plugin){
        this.plugin = plugin;
    }
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){

            double speed = plugin.getCustomSpeed().getSpeed(player);
            player.setWalkSpeed((float) speed);
        }
    }
}
