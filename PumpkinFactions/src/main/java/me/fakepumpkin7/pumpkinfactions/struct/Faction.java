package me.fakepumpkin7.pumpkinfactions.struct;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Faction {

    @Getter
    private String name;
    @Getter
    private HashMap<UUID, FactionRank> membersAndRank = new HashMap<>();

    @Getter
    Faction ally;

    //use FactionHandler to create new factions
    //FactionHandler should probably re-create the factions on startup, and save them to config
    protected Faction(Player leader, String name){
        this.membersAndRank.put(leader.getUniqueId(), FactionRank.LEADER);
        this.name = name;
    }

    public void addMember(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) != null){
            ChatUtils.error(player,"You already have a Faction!");
        } else {
            membersAndRank.put(player.getUniqueId(), FactionRank.MEMBER);
            ChatUtils.success(player,"Successfully joined " + name);
        }
    }

    public void removeMember(Player player){
        if(membersAndRank.get(player.getUniqueId()) == null ){
            return;
        }
        if(membersAndRank.get(player.getUniqueId()) == FactionRank.LEADER ){
            ChatUtils.warn(player, "Promote someone else to leader before you leave!");
            return;
        }
        membersAndRank.remove(player.getUniqueId());
    }
}
