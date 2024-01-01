package me.fakepumpkin7.pumpkincombat.customcombat.speed.listener;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InitCustomSpeedListener implements Listener {

    PumpkinCombat plugin;

    public InitCustomSpeedListener(PumpkinCombat p){
        this.plugin = p;
    }

    @EventHandler
    public void initCSonPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setMetadata("pumpkin-custom-speed", new FixedMetadataValue(plugin, 0.2));
    }
}
