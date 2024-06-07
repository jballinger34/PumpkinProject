package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionClaimChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class ClaimChangeListener implements Listener {

    @EventHandler
    public void onClaimChange(FactionClaimChangeEvent event){
        FactionConfigHandler.saveToConfig(event.getFaction());
    }
}