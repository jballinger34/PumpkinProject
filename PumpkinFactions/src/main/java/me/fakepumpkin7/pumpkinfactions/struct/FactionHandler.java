package me.fakepumpkin7.pumpkinfactions.struct;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class FactionHandler {

    static List<Faction> allFactions = new ArrayList<>();

    static Multimap<Faction, FChunk> factionToLandMap = HashMultimap.create();


    public static void createNewFaction(Player leader, String name){
        Faction faction = new Faction(leader, name);
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
            factionToLandMap.put(faction,chunk);
        }
    }
    public static void factionUnClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk).getName().equals(faction.getName())){
            factionToLandMap.remove(faction,chunk);
        }
    }
    public static Collection<FChunk> getFactionsClaims(Faction faction){
        return factionToLandMap.get(faction);
    }
    public static Faction getClaimAt(FChunk chunk){
        for(Faction f : factionToLandMap.keySet()){
            Collection<FChunk> chunks = factionToLandMap.get(f);
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
