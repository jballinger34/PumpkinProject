package me.fakepumpkin7.pumpkinframework.factions.event;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.factions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionDisbandEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    String faction;

    public FactionDisbandEvent(String faction){
        this.faction = faction;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}

}
