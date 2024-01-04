package me.fakepumpkin7.pumpkinframework.items.nbt;

import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTItem;

public class NbtUtil {

    public static boolean hasNbt(ItemStack item, String key) {
        return getNbtItem(item).hasKey(key);
    }
    public static NBTItem getNbtItem(ItemStack item) {
        return new NBTItem(item);
    }

    public static String getNbtString(ItemStack item, String key) {
        return getNbtItem(item).getString(key);
    }



    public static Integer getNbtInt(ItemStack item, String key) {
        return getNbtItem(item).getInteger(key);
    }

    public static Double getNbtDouble(ItemStack item, String key) {
        return getNbtItem(item).getDouble(key);
    }

    public static Boolean getNbtBoolean(ItemStack item, String key) {
        return getNbtItem(item).getBoolean(key);
    }

    public static Float getNbtFloat(ItemStack item, String key){
        return getNbtItem(item).getFloat(key);
    }
}
