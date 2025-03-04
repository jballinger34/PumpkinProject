package me.fakepumpkin7.pumpkincrates.crates.impl;

import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetManager;
import me.fakepumpkin7.pumpkinframework.armor.ArmourSetsAPI;
import me.fakepumpkin7.pumpkinframework.economy.EconomyAPI;
import me.fakepumpkin7.pumpkinframework.economy.EconomyManager;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantManager;
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
                new Reward(EnchantManager.getEnchantAPI().getEnchantItem(ItemRarity.MYTHIC,-1), 100),
                // need to properly implement getArmourItem
                // new Reward(ArmourSetManager.getArmourSetsAPI().getArmourItem().get(0), 100),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(1000.0), 200),
                new Reward(EconomyManager.getEconomyAPI().createMoneyNote(2000.0), 100)
        );
    }
}
