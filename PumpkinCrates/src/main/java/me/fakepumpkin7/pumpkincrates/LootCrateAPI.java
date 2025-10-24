package me.fakepumpkin7.pumpkincrates;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class LootCrateAPI implements me.fakepumpkin7.pumpkinframework.lootcrates.LootCrateAPI {
    private static LootCrateAPI instance = new LootCrateAPI();

    public static LootCrateAPI getInstance() {
        return instance;
    }

    @Override
    public ItemStack[] getCrateRewards(ItemRarity itemRarity) {
        Crate crate = CrateRegistry.getCrateByRarity(itemRarity).getCrate();
        if(crate == null){
            return new ItemStack[0];
        }
        ItemStack[] rewards = crate.generateRewards().toArray(new ItemStack[0]);
        return rewards;

    }
}
