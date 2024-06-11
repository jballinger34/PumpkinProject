package me.fakepumpkin7.pumpkinframework.economy;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Bank {

    private static HashMap<UUID, Double> bank = new HashMap<>();
    public static final String moneyNoteNBT = "pumpkin-money-note";

    public static void setBalance(UUID uuid, Double balance){
        bank.put(uuid, balance);
    }

    public static void addBalance(UUID uuid, Double toAdd){
        double newBal = bank.get(uuid) + toAdd;
        bank.put(uuid, newBal);
    }
    public static Double getBalance(UUID uuid){
        return bank.get(uuid);
    }
    public static boolean hasFunds(UUID uuid, Double amount){
        return (getBalance(uuid) >= amount);
    }
    public static void removeEntry(UUID uuid){
        bank.remove(uuid);
    }


    public static ItemStack createMoneyNote(String signer, Double amount){
        return new ItemBuilder(Material.PAPER)
                .setName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Money Note")
                .addLoreLine(ChatColor.WHITE.toString() +"Amount: " + amount)
                .addLoreLine(ChatColor.WHITE.toString() +"Signed by " + signer)
                .addNBT(moneyNoteNBT, amount)
                .build();
    }



















}
