package me.fakepumpkin7.pumpkinfactions.event;

import me.fakepumpkin7.pumpkinfactions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class FactionClaimChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();


    Faction faction;



    public FactionClaimChangeEvent(Faction faction){
        this.faction = faction;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}

    public Faction getFaction() {
        return faction;
    }
}
