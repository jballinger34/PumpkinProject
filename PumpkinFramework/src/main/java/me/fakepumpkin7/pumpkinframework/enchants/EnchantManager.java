package me.fakepumpkin7.pumpkinframework.enchants;

import org.bukkit.inventory.ItemStack;

public class EnchantManager {
    private static EnchantAPI enchantAPI;

    //in


    public static void setEnchantAPI(EnchantAPI api) {
        enchantAPI = api;
    }

    public static EnchantAPI getEnchantAPI() {
        if (enchantAPI == null) {
            throw new IllegalStateException("EnchantAPI implementation not set!");
        }
        return enchantAPI;
    }

}
