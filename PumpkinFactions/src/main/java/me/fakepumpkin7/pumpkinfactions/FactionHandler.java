package me.fakepumpkin7.pumpkinfactions;


import me.fakepumpkin7.pumpkinfactions.event.FactionClaimChangeEvent;
import me.fakepumpkin7.pumpkinfactions.event.FactionDisbandEvent;
import me.fakepumpkin7.pumpkinfactions.event.FactionMemberJoinLeaveEvent;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.factions.FactionAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class FactionHandler implements FactionAPI {


    private static FactionHandler instance;

    public static FactionHandler getInstance(){
        if(instance == null){
            instance = new FactionHandler();
        }
        return instance;
    }
    private FactionHandler(){}

    private List<Faction> allFactions = new ArrayList<>();

    //API METHODS

    public HashMap<UUID, String> getMembersAndRankPrefix(UUID uuid){
        Faction faction = getPlayersFaction(uuid);
        HashMap<UUID, FactionRank> map1 = faction.getMembersAndRank();
        HashMap<UUID, String> map2 = new HashMap<>();

        for(Map.Entry<UUID,FactionRank> e : map1.entrySet()){
            map2.put(e.getKey(),e.getValue().getPrefix());
        }
        return map2;

    }

    public String getFactionName(UUID uuid) {
        Faction faction = getPlayersFaction(uuid);
        if(faction == null) return "Wilderness";
        return faction.getName();
    }


    public boolean isSameFac(UUID uuid1, UUID uuid2){
        Faction f1 = getPlayersFaction(uuid1);
        Faction f2 = getPlayersFaction(uuid2);
        if(f1 == null || f2 == null) return false;
        if(f1.equals(f2)){
            return true;
        }
        return false;
    }

    public boolean isAlly(UUID uuid1, UUID uuid2){
        Faction f1 = getPlayersFaction(uuid1);
        Faction f2 = getPlayersFaction(uuid2);
        if(f1 == null || f2 == null) return false;
        if(f1.getAlly().equals(f2)){
            return true;
        }
        return false;
    }


    //Methods not exposed by API

    public void createNewFaction(Player leader, String name){
        Faction faction = new Faction(leader, name);
        allFactions.add(faction);
        Bukkit.getPluginManager().callEvent(new FactionMemberJoinLeaveEvent(faction));
    }
    public void loadFactionFromDisk(String name, HashMap<UUID, FactionRank> playerRankMap){
        Faction faction = new Faction(name, playerRankMap);
        allFactions.add(faction);
    }

    public Faction getPlayersFaction(String name){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if(offlinePlayer == null){
            return null;
        }
        UUID uuid = offlinePlayer.getUniqueId();
        return getPlayersFaction(uuid);
    }
    public Faction getPlayersFaction(UUID uuid){
        for(Faction faction :allFactions){
            for(UUID memberUUID: faction.getMembersAndRank().keySet()){
                if(memberUUID.toString().equalsIgnoreCase(uuid.toString())){
                    return faction;
                }
            }
        }
        return null;
    }
    public Faction getFactionFromName(String name){
        for(Faction faction : allFactions){
            if(faction.getName().equalsIgnoreCase(name)){
                return faction;
            }
        }
        return null;
    }


    public void factionClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk) == null){
            faction.getClaims().add(chunk);
        }
        //event called so factionconfighandler saves config
        Bukkit.getPluginManager().callEvent(new FactionClaimChangeEvent(faction));
    }
    public void factionUnClaimLand(Faction faction, FChunk chunk){
        if(getClaimAt(chunk).getName().equals(faction.getName())){
            //remove any warps in this chunk
            faction.removeWarpsInChunk(chunk);

            //remove chunk from faction
            faction.getClaims().remove(chunk);

        }
        //event called so factionconfighandler saves config
        Bukkit.getPluginManager().callEvent(new FactionClaimChangeEvent(faction));
    }
    public Faction getClaimAt(FChunk chunk){
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
    public Faction getClaimAt(String worldName, int x, int y) {
        FChunk chunk = new FChunk(worldName, x, y);
        return getClaimAt(chunk);
    }

    public boolean isClaimBorder(FChunk chunk, Faction owner){
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
    public void disbandFaction(Faction faction){
        //might need to do more here, but garbagecollector should deal w this.
        Bukkit.getPluginManager().callEvent(new FactionDisbandEvent(faction.getName()));

        allFactions.remove(faction);
    }

    @Override
    public boolean isWarzone(Location location) {
        return getClaimAt(new FChunk(location.getChunk())).getName().equalsIgnoreCase("warzone");
    }

    public List<Faction> getAllFactions() {
        return allFactions;
    }
}
