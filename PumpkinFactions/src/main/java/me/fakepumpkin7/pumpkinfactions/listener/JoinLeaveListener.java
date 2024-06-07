package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionClaimChangeEvent;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionMemberJoinLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoinLeave(FactionMemberJoinLeaveEvent event){
        FactionConfigHandler.saveToConfig(event.getFaction());
    }
}
