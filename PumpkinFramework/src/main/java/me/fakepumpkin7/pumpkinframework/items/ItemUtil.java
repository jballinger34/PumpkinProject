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


    public static boolean isHelmet(ItemStack item){
        if(!isValid(item)) return false;
        Material type = item.getType();
        if(     type == Material.DIAMOND_HELMET || type == Material.IRON_HELMET ||
                type == Material.CHAINMAIL_HELMET || type == Material.GOLD_HELMET ||
                type == Material.LEATHER_HELMET ||
                type == Material.SKULL_ITEM || type == Material.SKULL
        ) return true;
        return false;
    }

    public static boolean isChestplate(ItemStack item){
        if(!isValid(item)) return false;
        Material type = item.getType();
        if(     type == Material.DIAMOND_CHESTPLATE || type == Material.IRON_CHESTPLATE||
                type == Material.CHAINMAIL_CHESTPLATE || type == Material.GOLD_CHESTPLATE ||
                type == Material.LEATHER_CHESTPLATE
        ) return true;
        return false;
    }

    public static boolean isLeggings(ItemStack item){
        if(!isValid(item)) return false;
        Material type = item.getType();
        if(     type == Material.DIAMOND_LEGGINGS || type == Material.IRON_LEGGINGS ||
                type == Material.CHAINMAIL_LEGGINGS || type == Material.GOLD_LEGGINGS ||
                type == Material.LEATHER_LEGGINGS
        ) return true;
        return false;
    }

    public static boolean isBoots(ItemStack item){
        if(!isValid(item)) return false;
        Material type = item.getType();
        if(     type == Material.DIAMOND_BOOTS || type == Material.IRON_BOOTS ||
                type == Material.CHAINMAIL_BOOTS || type == Material.GOLD_BOOTS ||
                type == Material.LEATHER_BOOTS
        ) return true;
        return false;
    }

}
