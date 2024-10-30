package com.rit.sucy;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class EnchantItemManager {

    private static EnchantItem impl;

    public static void setEnchantItem(EnchantItem enchantItem){
        impl = enchantItem;
    }

    public static ItemStack getEnchantItem(CustomEnchantment enchantment, int level){
        if(impl == null){
            throw new IllegalStateException("EnchantItem implementation not set!");
        }
        return impl.getEnchantItem(enchantment, level);
    }



}
