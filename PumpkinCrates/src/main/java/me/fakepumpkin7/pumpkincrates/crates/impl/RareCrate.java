package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetManager;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class RareCrate extends Crate {

    public RareCrate(){
        super(ChatColor.BOLD + "Rare Crate", "rare-crate", ItemRarity.RARE);
        minDrops = 2;
        maxDrops = 4;
    }

    @Override
    protected void rollCrateRewards() {

        this.rewards = Arrays.asList(
                // need to properly implement getArmourItem
                new Reward(ArmourSetManager.getArmourSetsAPI().getArmourSet().get(1), 100),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(5000.0), 200),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(10000.0), 100)
        );
    }
}
