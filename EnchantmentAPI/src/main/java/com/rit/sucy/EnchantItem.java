package com.rit.sucy;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public interface EnchantItem {

    ItemStack getEnchantItem(CustomEnchantment customEnchantment, int level);
}
