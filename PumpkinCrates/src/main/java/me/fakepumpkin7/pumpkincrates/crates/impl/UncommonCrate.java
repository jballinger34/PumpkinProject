package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetManager;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class UncommonCrate extends Crate {
    public UncommonCrate(){
        super(ChatColor.BOLD + "Uncommon Crate", "uncommon-crate", ItemRarity.UNCOMMON);
        minDrops = 2;
        maxDrops = 4;
    }

    @Override
    protected void rollCrateRewards() {

        this.rewards = Arrays.asList(
                // need to properly implement getArmourItem
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(2000.0), 200),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(6000.0), 100)
        );
    }
}
