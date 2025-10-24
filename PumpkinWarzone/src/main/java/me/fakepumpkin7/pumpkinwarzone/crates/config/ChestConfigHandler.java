package me.fakepumpkin7.pumpkinwarzone.crates.config;

import me.fakepumpkin7.pumpkinwarzone.PumpkinWarzone;
import me.fakepumpkin7.pumpkinwarzone.crates.listener.ChestListener;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class ChestConfigHandler {

    private final static Random random = new Random();

    private static String chestsSectionString = "chest-locations";
    public static void saveChestsToConfig( ){
        FileConfiguration config = PumpkinWarzone.getInstance().getConfig();
        if(ChestListener.chestAccessHistory.keySet().size() == 0) {
            return;
        }

        config.set(chestsSectionString, ChestListener.chestAccessHistory.keySet());

        PumpkinWarzone.getInstance().saveConfig();
    }
    public static void loadChestsFromConfig(){
        FileConfiguration config = PumpkinWarzone.getInstance().getConfig();
        Set<Location> locations = (Set<Location>) config.get(chestsSectionString);

        if(locations == null){
            return;
        }

        for(Location loc : locations){
            //chests will restock 2-5 mins after a reboot
            ChestListener.chestAccessHistory.put(loc, System.currentTimeMillis() + (random.nextInt(3) + 2) * 60L * 1000L);
        }
    }


}
