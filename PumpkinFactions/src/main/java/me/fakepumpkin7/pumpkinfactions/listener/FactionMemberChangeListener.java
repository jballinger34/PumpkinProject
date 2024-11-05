package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
import me.fakepumpkin7.pumpkinfactions.event.FactionDisbandEvent;
import me.fakepumpkin7.pumpkinfactions.event.FactionMemberJoinLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionMemberChangeListener implements Listener {

    @EventHandler
    public void onJoinLeave(FactionMemberJoinLeaveEvent event){
        FactionConfigHandler.saveMembersToConfig(event.getFaction());
    }
    @EventHandler
    public void onDisband(FactionDisbandEvent event){
        FactionConfigHandler.removeFaction(event.getFaction());
    }
}
