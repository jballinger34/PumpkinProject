package me.fakepumpkin7.pumpkinframework.enchants;

import com.rit.sucy.EnchantmentAPI;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

public class EnchantUtils {

    //these methods are the same as the ones in EnchantmentAPI
    //just as this is in framework it gives access to other places in the code
    public static ItemStack getEnchant(){
        return EnchantmentAPI.getEnchantItem();
    }
    public static ItemStack getEnchant(String enchantName){
        return EnchantmentAPI.getEnchantItem(enchantName);
    }
    public static ItemStack getEnchant(ItemRarity rarity){
        return EnchantmentAPI.getEnchantItem(rarity);
    }
}
