package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetManager;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantManager;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class HeroicCrate extends Crate {

    public HeroicCrate(){
        super(ChatColor.BOLD + "Heroic Crate", "heroic-crate", ItemRarity.HEROIC);
        minDrops = 3;
        maxDrops = 5;
    }

    @Override
    protected void rollCrateRewards() {

        this.rewards = Arrays.asList(
                // need to properly implement getArmourItem
                new Reward(ArmourSetManager.getArmourSetsAPI().getArmourSet().get(0), 100),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(20000.0), 200),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(40000.0), 100)
        );
    }
}
