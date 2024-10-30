package me.fakepumpkin7.pumpkinframework.enchants;

import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

public interface EnchantAPI {

    //these methods are the same as the ones in EnchantmentAPI
    //just as this is in framework it gives access to other places in the code
    ItemStack getEnchantItem();

    //put level to -1 for random level
    ItemStack getEnchantItem(String enchantName, int level);
    ItemStack getEnchantItem(ItemRarity rarity, int level);

    void addEnchant(String name, int level, ItemStack toAdd);




}
