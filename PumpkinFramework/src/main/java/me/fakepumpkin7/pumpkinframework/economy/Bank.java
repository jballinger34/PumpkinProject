package me.fakepumpkin7.pumpkinframework.economy;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class Bank {

    private static HashMap<UUID, Double> bank = new HashMap<>();

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





















}
