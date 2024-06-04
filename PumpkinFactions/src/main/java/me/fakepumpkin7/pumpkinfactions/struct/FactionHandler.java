package me.fakepumpkin7.pumpkinfactions.struct;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionHandler {

    static List<Faction> allFactions = new ArrayList<>();



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










}
