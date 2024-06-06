package me.fakepumpkin7.pumpkinfactions.config;

import me.fakepumpkin7.pumpkinfactions.PumpkinFactions;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.Faction;
import me.fakepumpkin7.pumpkinfactions.struct.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
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
        }

    }

    //TODO WORK ON THIS SO ITS DOESN'T STORE AS MUCH REDUNDANT INFO
    //TODO SPLIT THIS UP INTO SAVE MEMBERS, SAVE CHUNKS, ETC IF ANYTHING ELSE ADDED
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

        if(factionSection.getConfigurationSection("faction-members") == null){
            factionSection.createSection("faction-members");
        }
        ConfigurationSection membersSection = factionSection.getConfigurationSection("faction-members");
        for(UUID member : faction.getMembersAndRank().keySet()){
            membersSection.set(member.toString(), faction.getMembersAndRank().get(member).ordinal());
        }

        if(factionSection.getConfigurationSection("faction-claims") == null){
            factionSection.createSection("faction-claims");
        }
        ConfigurationSection claimsSection = factionSection.getConfigurationSection("faction-claims");

        claimsSection.set("claims-string", serializeFChunks(faction.getClaims()));


        PumpkinFactions.getInstance().saveConfig();
    }
    public static void saveAllToConfig(){
        for(Faction faction : FactionHandler.getAllFactions()){
            saveToConfig(faction);
        }
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
}
