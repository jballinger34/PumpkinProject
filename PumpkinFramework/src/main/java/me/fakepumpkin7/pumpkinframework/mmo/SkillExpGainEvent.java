package me.fakepumpkin7.pumpkinframework.mmo;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillExpGainEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    public SkillExpGainEvent(Player player, String skillName, double exp) {
        this.player = player;
        this.skillName = skillName;
        this.exp = exp;
    }

    private final Player player;
    private final String skillName;
    private final double exp;

    private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public String getSkillName() {
        return skillName;
    }

    public double getExp() {
        return exp;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
