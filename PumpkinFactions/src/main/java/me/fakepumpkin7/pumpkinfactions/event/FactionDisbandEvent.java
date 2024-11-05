package me.fakepumpkin7.pumpkinfactions.event;


import lombok.Getter;
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
