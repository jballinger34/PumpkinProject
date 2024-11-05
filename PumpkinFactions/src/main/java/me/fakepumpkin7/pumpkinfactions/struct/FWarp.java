package me.fakepumpkin7.pumpkinfactions.struct;


import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.player.teleport.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
public class FWarp {
    Location location;
    FactionRank rankNeeded;
    String name;

    public FWarp(String name, Location location, FactionRank rankneeded){
        this.location = location;
        this.name = name;
        this.rankNeeded = rankneeded;
    }

    public void warpHere(Player player){
        TeleportUtils.timedTeleport(player, location);
    }

}
