package me.fakepumpkin7.pumpkinfactions.config;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.PumpkinFactions;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.FWarp;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class FactionConfigHandler {

    //Strings used in config layout
    private static String configSectionString = "factions";
    private static String claimsSectionString = "faction-claims";
    private static String claimsStringName = "claims-string";
    private static String membersSectionString = "faction-members";
    private static String warpsSectionString = "faction-warps";
    private static String warpsStringName = "all-warps";

    public static void loadFromConfig(){
        ConfigurationSection cs = PumpkinFactions.getInstance().getConfig().getConfigurationSection(configSectionString);
        if(cs == null) return;

        for(String facName : cs.getKeys(false)){
            ConfigurationSection facSection = cs.getConfigurationSection(facName);
            if(facSection == null) continue;

            ConfigurationSection membersConfig = facSection.getConfigurationSection(membersSectionString);

            HashMap<UUID, FactionRank> memberRankMap = new HashMap<>();

            //warzone members may be null
            if(membersConfig != null){
                for(String uuidStr : membersConfig.getKeys(false)){
                    UUID uuid = UUID.fromString(uuidStr);
                    FactionRank factionRank = FactionRank.values()[membersConfig.getInt(uuidStr)];
                    memberRankMap.put(uuid,factionRank);
                }
            }
            FactionHandler.loadFactionFromDisk(facName, memberRankMap);
            Faction faction = FactionHandler.getFactionFromName(facName);
            if(faction == null){
                System.out.println("Problem Loading Faction From Disk");
                break;
            }

            ConfigurationSection claimsConfig = facSection.getConfigurationSection(claimsSectionString);

            List<FChunk> chunkList = deserializeFChunks(claimsConfig.getString(claimsStringName));
            faction.getClaims().addAll(chunkList);

            ConfigurationSection warpsConfig = facSection.getConfigurationSection(warpsSectionString);
            List<FWarp> warps = deserializeFWarps(warpsConfig.getString(warpsStringName));
            faction.getWarps().addAll(warps);

        }

    }


    public static void saveAllToConfig(){
        for(Faction faction : FactionHandler.getAllFactions()){
            saveFactionToConfig(faction);
        }
    }

    private static void saveFactionToConfig(Faction faction){
        ConfigurationSection factionSection = getFactionSection(faction);

        ConfigurationSection claimsSection = getClaimsSection(faction);
        saveClaims(claimsSection, faction);

        //we deal with members and their ranks differently, so use factionSection.
        saveMembers(factionSection, faction);

        ConfigurationSection warpsSection = getWarpsSection(faction);
        saveWarps(warpsSection, faction);

        PumpkinFactions.getInstance().saveConfig();
    }

    public static void saveClaimsToConfig(Faction faction){
        saveClaims(getClaimsSection(faction), faction);
        PumpkinFactions.getInstance().saveConfig();
    }
    public static void saveMembersToConfig(Faction faction){
        saveMembers(getFactionSection(faction), faction);
        PumpkinFactions.getInstance().saveConfig();
    }
    public static void saveWarpsToConfig(Faction faction){
        saveClaims(getWarpsSection(faction), faction);
        PumpkinFactions.getInstance().saveConfig();
    }


    //TODO WORK ON THIS SO ITS DOESN'T STORE AS MUCH REDUNDANT INFO
    // SPLIT THIS UP INTO SAVE MEMBERS, SAVE CHUNKS, ETC IF ANYTHING ELSE ADDED
    // this will reduce overall load, as this will be called whenever members change, land claimed etc

    private static void saveClaims(ConfigurationSection claimsSection, Faction faction){
        claimsSection.set(claimsStringName, serializeFChunks(faction.getClaims()));
    }
    private static void saveMembers(ConfigurationSection factionSection, Faction faction){
        factionSection.set(membersSectionString, null);
        ConfigurationSection membersSection = factionSection.createSection(membersSectionString);

        for(UUID member : faction.getMembersAndRank().keySet()){
            membersSection.set(member.toString(), faction.getMembersAndRank().get(member).ordinal());
        }
    }
    private static void saveWarps(ConfigurationSection warpsSection, Faction faction){
        warpsSection.set(warpsStringName, serializeFWarps(faction.getWarps()));
    }

    public static void removeFaction(String factionName){
        PumpkinFactions.getInstance().getConfig().getConfigurationSection(configSectionString).set(factionName, null);
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


    private static ConfigurationSection getConfigSection(Faction faction){
        if(PumpkinFactions.getInstance().getConfig().getConfigurationSection(configSectionString) == null){
            PumpkinFactions.getInstance().getConfig().createSection(configSectionString);
        }
        return PumpkinFactions.getInstance().getConfig().getConfigurationSection(configSectionString);
    }
    private static ConfigurationSection getFactionSection(Faction faction){
        ConfigurationSection section = getConfigSection(faction);

        String factionName = faction.getName();
        if(section.getConfigurationSection(factionName) == null){
            section.createSection(factionName);
        }
        return section.getConfigurationSection(factionName);
    }
    private static ConfigurationSection getClaimsSection(Faction faction){
        ConfigurationSection factionSection = getFactionSection(faction);
        if(factionSection.getConfigurationSection(claimsSectionString) == null){
            factionSection.createSection(claimsSectionString);
        }
        return factionSection.getConfigurationSection(claimsSectionString);
    }
    private static ConfigurationSection getWarpsSection(Faction faction) {
        ConfigurationSection factionSection = getFactionSection(faction);
        if(factionSection.getConfigurationSection(warpsSectionString) == null){
            factionSection.createSection(warpsSectionString);
        }
        return factionSection.getConfigurationSection(warpsSectionString);

    }



}
