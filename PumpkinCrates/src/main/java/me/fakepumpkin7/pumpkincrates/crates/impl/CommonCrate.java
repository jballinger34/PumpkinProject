package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetsAPI;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class CommonCrate extends Crate {

    public CommonCrate(){
        super(ChatColor.BOLD + "Common Crate", "common-crate", ItemRarity.COMMON);
        minDrops = 2;
        maxDrops = 4;
    }

    @Override
    protected void rollCrateRewards() {

        this.rewards = Arrays.asList(
                new Reward(EnchantAPI.getEnchant(ItemRarity.MYTHIC), 100),
                new Reward(ArmourSetsAPI.getArmourItem(), 100),
                new Reward(Bank.createMoneyNote("Server", 1000.0), 200),
                new Reward(Bank.createMoneyNote("Server", 2000.0), 100)
        );
    }
}
