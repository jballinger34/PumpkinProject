package me.fakepumpkin7.pumpkinfactions.struct;

import lombok.Getter;
import me.fakepumpkin7.pumpkinfactions.config.FactionConfigHandler;
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
        FactionConfigHandler.saveToConfig(faction);
    }
    public static void factionUnClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk).getName().equals(faction.getName())){
            faction.getClaims().remove(chunk);
        }
        FactionConfigHandler.saveToConfig(faction);
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





}
