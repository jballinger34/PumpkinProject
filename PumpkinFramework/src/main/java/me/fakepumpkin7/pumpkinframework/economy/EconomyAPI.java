package me.fakepumpkin7.pumpkinframework.economy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface EconomyAPI {

    void setBalance(UUID uuid , Double balance);

    void addBalance(UUID uuid, Double toAdd);

    Double getBalance(UUID uuid);

    boolean hasFunds(UUID uuid, Double amount);

    ItemStack createMoneyNote(Double amount);

    ItemStack createMoneyNote(String signer, Double amount);
    String getCurrencyString();

}
