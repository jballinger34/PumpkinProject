package me.fakepumpkin7.pumpkinframework.event.economy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class MoneyTransferEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player sender;
    @Getter
    private final Player receiver;

    @Getter
    private final double amountSent;


    @Getter @Setter
    private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}
