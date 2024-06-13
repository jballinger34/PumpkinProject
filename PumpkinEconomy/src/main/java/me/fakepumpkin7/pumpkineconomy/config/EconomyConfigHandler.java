package me.fakepumpkin7.pumpkineconomy.config;

import me.fakepumpkin7.pumpkineconomy.PumpkinEconomy;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class EconomyConfigHandler implements Listener {

    static String balanceSectionString = "balances";

    public static void updateBalanceInConfig(UUID uuid){
        double currentBal = Bank.getBalance(uuid);

        FileConfiguration config = PumpkinEconomy.getInstance().getConfig();
        if(config.getConfigurationSection(balanceSectionString) == null){
            config.createSection(balanceSectionString);
        }
        ConfigurationSection balancesSection = config.getConfigurationSection(balanceSectionString);

        balancesSection.set(uuid.toString(), currentBal);


        PumpkinEconomy.getInstance().saveConfig();
    }
    public static Double getBalanceFromConfig(UUID uuid){
        FileConfiguration config = PumpkinEconomy.getInstance().getConfig();
        if(config.getConfigurationSection(balanceSectionString) == null){
            System.out.println("Balance section of PumpkinEconomy config doesn't exist, it should.");
            return null;
        }
        ConfigurationSection balancesSection = config.getConfigurationSection(balanceSectionString);

        if(balancesSection.contains(uuid.toString())){
            return balancesSection.getDouble(uuid.toString());
        }
        return null;
    }

    public static HashMap<String, Double> getAllBalancesFromConfig(){
        FileConfiguration config = PumpkinEconomy.getInstance().getConfig();
        if(config.getConfigurationSection(balanceSectionString) == null){
            System.out.println("Balance section of PumpkinEconomy config doesn't exist, it should.");
            return null;
        }
        HashMap<String, Double> toReturn = new HashMap<>();
        ConfigurationSection balancesSection = config.getConfigurationSection(balanceSectionString);

        for(String key : balancesSection.getKeys(false)){
            String str = key;
            Double balance = balancesSection.getDouble(key);
            toReturn.put(str,balance);
        }
        return toReturn;

    }


    public static void saveAllPlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID id = player.getUniqueId();

            updateBalanceInConfig(id);
        }
    }

}
