package me.fakepumpkin7.pumpkincombat.customcombat.speed;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.listener.InitCustomSpeedListener;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.tasks.UpdateCustomSpeedTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CustomSpeed {

    PumpkinCombat plugin;

    public CustomSpeed(PumpkinCombat plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InitCustomSpeedListener(plugin), plugin);

        UpdateCustomSpeedTask speedTask = new UpdateCustomSpeedTask(plugin);
        speedTask.runTaskTimer(plugin, 20, 100);

    }


    public double getPlayerSpeed(Player player){
        double speed = player.getWalkSpeed();

        if(player.hasMetadata("pumpkin-custom-speed")){
            speed = player.getMetadata("pumpkin-custom-speed").get(0).asDouble();
        }

        return speed;
    }

    public void setPlayerSpeed(Player player, double speed){
        player.setMetadata("pumpkin-custom-speed", new FixedMetadataValue(plugin, speed));
    }

}
