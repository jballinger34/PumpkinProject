package me.fakepumpkin7.pumpkinframework.enchants;

import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

public interface EnchantAPI {


    ItemStack getEnchantItem();

    //put level to -1 for random level
    ItemStack getEnchantItem(String enchantName, int level);
    ItemStack getEnchantItem(ItemRarity rarity, int level);

    void addEnchant(String name, int level, ItemStack toAdd);

}
