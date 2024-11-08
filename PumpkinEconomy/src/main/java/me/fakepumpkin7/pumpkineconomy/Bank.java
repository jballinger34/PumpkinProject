package me.fakepumpkin7.pumpkineconomy;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.economy.EconomyAPI;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Bank implements EconomyAPI {

    @Getter private static Bank instance = new Bank();

    private HashMap<UUID, Double> bank = new HashMap<>();
    public static final String moneyNoteNBT = "pumpkin-money-note";

    public void setBalance(UUID uuid, Double balance){
        bank.put(uuid, balance);
    }

    public void addBalance(UUID uuid, Double toAdd){
        double newBal = bank.get(uuid) + toAdd;
        bank.put(uuid, newBal);
    }
    public Double getBalance(UUID uuid){
        return bank.get(uuid);
    }
    public boolean hasFunds(UUID uuid, Double amount){
        return (getBalance(uuid) >= amount);
    }
    public void removeEntry(UUID uuid){
        bank.remove(uuid);
    }

    public ItemStack createMoneyNote(Double amount){
        return createMoneyNote("Server", amount);
    }


    public ItemStack createMoneyNote(String signer, Double amount){
        return new ItemBuilder(Material.PAPER)
                .setName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Money Note")
                .addLoreLine(ChatColor.GRAY +"Amount: " + amount)
                .addLoreLine(ChatColor.GRAY + "Signed by " + signer)
                .addNBT(moneyNoteNBT, amount)
                .build();
    }

    public String getCurrencyString() {
        return "$";
    }


}
