package me.fakepumpkin7.pumpkinframework.factions;

import lombok.Getter;
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
        player.teleport(location);
    }

}
