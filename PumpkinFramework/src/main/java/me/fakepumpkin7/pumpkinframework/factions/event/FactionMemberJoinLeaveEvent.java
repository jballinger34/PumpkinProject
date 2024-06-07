package me.fakepumpkin7.pumpkinframework.factions.event;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.factions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionMemberJoinLeaveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    Faction faction;



    public FactionMemberJoinLeaveEvent(Faction faction){
        this.faction = faction;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}

}
