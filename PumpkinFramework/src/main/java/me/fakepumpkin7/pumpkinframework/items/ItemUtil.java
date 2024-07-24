package me.fakepumpkin7.pumpkinframework.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public static boolean isValid(ItemStack item){
        if(item == null || item.getAmount() == 0 || item.getType() == Material.AIR){
            return false;
        }
        return true;
    }


    public static ItemStack hideAttributes(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack addGlow(ItemStack item){
        if(item.getType() == Material.FISHING_ROD){
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        } else {
            item.addUnsafeEnchantment(Enchantment.LURE, 1);
        }
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }

}
