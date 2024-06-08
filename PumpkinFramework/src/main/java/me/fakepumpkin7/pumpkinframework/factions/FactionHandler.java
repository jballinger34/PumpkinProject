package me.fakepumpkin7.pumpkinframework.factions;


import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionClaimChangeEvent;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionDisbandEvent;
import me.fakepumpkin7.pumpkinframework.factions.event.FactionMemberJoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class FactionHandler {

    @Getter
    private static List<Faction> allFactions = new ArrayList<>();




    public static void createNewFaction(Player leader, String name){
        Faction faction = new Faction(leader, name);
        allFactions.add(faction);
        Bukkit.getPluginManager().callEvent(new FactionMemberJoinLeaveEvent(faction));
    }
    public static void loadFactionFromDisk(String name, HashMap<UUID, FactionRank> playerRankMap){
        Faction faction = new Faction(name, playerRankMap);
        allFactions.add(faction);
    }

    public static Faction getPlayersFaction(String name){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if(offlinePlayer == null){
            return null;
        }
        UUID uuid = offlinePlayer.getUniqueId();
        return getPlayersFaction(uuid);
    }
    public static Faction getPlayersFaction(UUID uuid){
        for(Faction faction :allFactions){
            for(UUID memberUUID: faction.getMembersAndRank().keySet()){
                if(memberUUID.toString().equalsIgnoreCase(uuid.toString())){
                    return faction;
                }
            }
        }
        return null;
    }
    public static Faction getFactionFromName(String name){
        for(Faction faction : allFactions){
            if(faction.getName().equalsIgnoreCase(name)){
                return faction;
            }
        }
        return null;
    }

    public static boolean isInSameFaction(Player player1, Player player2){
        Faction f1 = getPlayersFaction(player1.getUniqueId());
        Faction f2 = getPlayersFaction(player2.getUniqueId());
        if(f1 == null || f2 == null) return false;
        if(f1.equals(f2)){
            return true;
        }
        return false;
    }

    public static boolean isAlly(Player p1, Player p2){
        Faction f1 = getPlayersFaction(p1.getUniqueId());
        Faction f2 = getPlayersFaction(p2.getUniqueId());
        if(f1 == null || f2 == null) return false;
        if(f1.getAlly().equals(f2)){
            return true;
        }
        return false;
    }

    public static void factionClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk) == null){
            faction.getClaims().add(chunk);
        }
        //event called so factionconfighandler saves config
        Bukkit.getPluginManager().callEvent(new FactionClaimChangeEvent(faction));
    }
    public static void factionUnClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk).getName().equals(faction.getName())){
            //remove any warps in this chunk
            faction.removeWarpsInChunk(chunk);

            //remove chunk from faction
            faction.getClaims().remove(chunk);

        }
        //event called so factionconfighandler saves config
        Bukkit.getPluginManager().callEvent(new FactionClaimChangeEvent(faction));
    }
    public static Faction getClaimAt(FChunk chunk){
        for(Faction f : allFactions){
            Collection<FChunk> chunks = f.getClaims();
            for(FChunk chunk1 : chunks){
                if (chunk.equals(chunk1)){
                    return f;
                }
            }
        }
        return null;
    }
    public static Faction getClaimAt(String worldName, int x, int y) {
        FChunk chunk = new FChunk(worldName, x, y);
        return getClaimAt(chunk);
    }

    public static boolean isClaimBorder(FChunk chunk, Faction owner){
        int x = chunk.getX();
        int y = chunk.getY();
        String worldName = chunk.getWorldName();

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                FChunk toCheck = new FChunk(worldName, x+i,y+j);
                if(getClaimAt(toCheck) == null || !getClaimAt(toCheck).getName().equals(owner.getName())){
                    return true;
                }
            }
        }
        return false;
    }
    public static void disbandFaction(Faction faction){
        //might need to do more here, but garbagecollector should deal w this.
        Bukkit.getPluginManager().callEvent(new FactionDisbandEvent(faction.getName()));

        allFactions.remove(faction);
    }



}
