package me.fakepumpkin7.pumpkinfactions.config;

import me.fakepumpkin7.pumpkinfactions.PumpkinFactions;
import me.fakepumpkin7.pumpkinframework.factions.*;
import me.fakepumpkin7.pumpkinframework.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class FactionConfigHandler {

    public static void loadFromConfig(){
        ConfigurationSection cs = PumpkinFactions.getInstance().getConfig().getConfigurationSection("factions");
        if(cs == null) return;

        for(String facName : cs.getKeys(false)){
            ConfigurationSection facSection = cs.getConfigurationSection(facName);
            if(facSection == null) continue;

            ConfigurationSection membersConfig = facSection.getConfigurationSection("faction-members");

            HashMap<UUID,FactionRank> memberRankMap = new HashMap();

            for(String uuidStr : membersConfig.getKeys(false)){
                UUID uuid = UUID.fromString(uuidStr);
                FactionRank factionRank = FactionRank.values()[membersConfig.getInt(uuidStr)];
                memberRankMap.put(uuid,factionRank);
            }
            FactionHandler.loadFactionFromDisk(facName, memberRankMap);
            Faction faction = FactionHandler.getFactionFromName(facName);
            if(faction == null){
                System.out.println("Problem Loading Faction From Disk");
            }

            ConfigurationSection claimsConfig = facSection.getConfigurationSection("faction-claims");

            List<FChunk> chunkList = deserializeFChunks(claimsConfig.getString("claims-string"));
            faction.getClaims().addAll(chunkList);

            ConfigurationSection warpsConfig = facSection.getConfigurationSection("faction-warps");
            List<FWarp> warps = deserializeFWarps(warpsConfig.getString("all-warps"));
            faction.getWarps().addAll(warps);

        }

    }

    //TODO WORK ON THIS SO ITS DOESN'T STORE AS MUCH REDUNDANT INFO
    // SPLIT THIS UP INTO SAVE MEMBERS, SAVE CHUNKS, ETC IF ANYTHING ELSE ADDED
    // this will reduce overall load, as this will be called whenever members change, land claimed etc
    public static void saveToConfig(Faction faction){
        if(PumpkinFactions.getInstance().getConfig().getConfigurationSection("factions") == null){
            PumpkinFactions.getInstance().getConfig().createSection("factions");
        }
        ConfigurationSection section = PumpkinFactions.getInstance().getConfig().getConfigurationSection("factions");

        String factionName = faction.getName();
        if(section.getConfigurationSection(factionName) == null){
            section.createSection(factionName);
        }
        ConfigurationSection factionSection = section.getConfigurationSection(factionName);

        //deal with members and their ranks.
        //as this isnt a serialised string, we have to wipe all and readd
        factionSection.set("faction-members", null);
        ConfigurationSection membersSection = factionSection.createSection("faction-members");

        for(UUID member : faction.getMembersAndRank().keySet()){
            membersSection.set(member.toString(), faction.getMembersAndRank().get(member).ordinal());
        }

        //deal with claims
        if(factionSection.getConfigurationSection("faction-claims") == null){
            factionSection.createSection("faction-claims");
        }
        ConfigurationSection claimsSection = factionSection.getConfigurationSection("faction-claims");

        claimsSection.set("claims-string", serializeFChunks(faction.getClaims()));

        //deal with warps
        if(factionSection.getConfigurationSection("faction-warps") == null){
            factionSection.createSection("faction-warps");
        }
        ConfigurationSection warpsSection = factionSection.getConfigurationSection("faction-warps");

        warpsSection.set("all-warps", serializeFWarps(faction.getWarps()));

        PumpkinFactions.getInstance().saveConfig();
    }
    public static void saveAllToConfig(){
        for(Faction faction : FactionHandler.getAllFactions()){
            saveToConfig(faction);
        }
    }
    public static void removeFaction(String factionName){
        PumpkinFactions.getInstance().getConfig().getConfigurationSection("factions").set(factionName, null);
        PumpkinFactions.getInstance().saveConfig();
    }
    private static String serializeFChunks(List<FChunk> chunkList){
        String toReturn = "";
        for(FChunk chunk : chunkList){

            String x = String.valueOf(chunk.getX());
            String y = String.valueOf(chunk.getY());
            String worldName = chunk.getWorldName();

            toReturn = toReturn + x + "," + y + "," + worldName + ":";
        }
        return toReturn;

    }
    private static List<FChunk> deserializeFChunks(String chunksStr){
        List<FChunk> toReturn = new ArrayList<>();

        if(chunksStr == null || chunksStr.equals("")){
            return toReturn;
        }

        String[] chunkStrList = chunksStr.split(":");
        for(String chunkStr : chunkStrList){
            String[] chunkInfo = chunkStr.split(",");

            int x = Integer.parseInt(chunkInfo[0]);
            int y = Integer.parseInt(chunkInfo[1]);
            String worldName = chunkInfo[2];

            FChunk chunk = new FChunk(worldName,x,y);
            toReturn.add(chunk);
        }
        return toReturn;
    }

    private static String serializeFWarps(List<FWarp> warpList){
        String toReturn = "";
        for(FWarp warp : warpList){

            String x = String.valueOf(warp.getLocation().getX());
            String y = String.valueOf(warp.getLocation().getY());
            String z = String.valueOf(warp.getLocation().getZ());

            String worldName = warp.getLocation().getWorld().getName();
            String warpName = warp.getName();
            String facRankOrdinalString = String.valueOf(warp.getRankNeeded().ordinal());

            toReturn = toReturn + x + "," + y + "," + z +  "," + worldName + "," + warpName + "," + facRankOrdinalString +":";
        }
        return toReturn;
    }
    private static List<FWarp> deserializeFWarps(String warpsStr){
        List<FWarp> toReturn = new ArrayList<>();

        if(warpsStr == null || warpsStr.equals("")){
            return toReturn;
        }

        String[] warpStrList = warpsStr.split(":");
        for(String warpStr : warpStrList){
            String[] warpInfo = warpStr.split(",");

            double x = Double.parseDouble(warpInfo[0]);
            double y = Double.parseDouble(warpInfo[1]);
            double z = Double.parseDouble(warpInfo[2]);
            String worldName = warpInfo[3];
            String warpName = warpInfo[4];
            FactionRank factionRank = FactionRank.values()[Integer.parseInt(warpInfo[5])];


            Location location = new Location(Bukkit.getWorld(worldName),x,y,z);
            FWarp warp = new FWarp(warpName,location, factionRank);
            toReturn.add(warp);
        }
        return toReturn;
    }


}
