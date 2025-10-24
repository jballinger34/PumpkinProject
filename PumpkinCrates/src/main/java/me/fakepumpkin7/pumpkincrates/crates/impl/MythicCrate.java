package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetManager;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class MythicCrate extends Crate {

    public MythicCrate() {
        super(ChatColor.BOLD + "Mythic Crate", "mythic-crate", ItemRarity.MYTHIC);
        minDrops = 2;
        maxDrops = 4;
    }

    @Override
    protected void rollCrateRewards() {

        this.rewards = Arrays.asList(
                new Reward(ArmourSetManager.getArmourSetsAPI().getArmourSet().get(2), 100),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(20000.0), 200),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(30000.0), 100)
        );
    }
}