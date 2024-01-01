package me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InitCustomDefenceListener implements Listener {

    PumpkinCombat plugin;

    public InitCustomDefenceListener(PumpkinCombat p){
        this.plugin = p;
    }

    @EventHandler
    public void initCDonPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setMetadata("pumpkin-custom-defence", new FixedMetadataValue(plugin, 0));
    }
}
