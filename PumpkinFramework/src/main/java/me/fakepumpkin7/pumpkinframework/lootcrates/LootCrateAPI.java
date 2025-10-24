package me.fakepumpkin7.pumpkinframework.lootcrates;

import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

public interface LootCrateAPI {

    ItemStack[] getCrateRewards(ItemRarity rarity);

}
