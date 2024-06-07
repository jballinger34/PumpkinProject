package me.fakepumpkin7.pumpkinframework.factions.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.fakepumpkin7.pumpkinframework.factions.Faction;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class FactionClaimChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    Faction faction;



    public FactionClaimChangeEvent(Faction faction){
        this.faction = faction;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}


}
