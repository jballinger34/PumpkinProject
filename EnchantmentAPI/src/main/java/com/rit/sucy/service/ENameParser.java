//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ENameParser {
    private static final Map<String, String> eNames = new HashMap<String, String>() {
        {
            this.put("knockback", "KNOCKBACK");
            this.put("looting", "LOOT_BONUS_MOBS");
            this.put("sharpness", "DAMAGE_ALL");
            this.put("smite", "DAMAGE_UNDEAD");
            this.put("bane of arthropods", "DAMAGE_ARTHROPODS");
            this.put("fire aspect", "FIRE_ASPECT");
            this.put("infinity", "ARROW_INFINITE");
            this.put("flame", "ARROW_FIRE");
            this.put("punch", "ARROW_KNOCKBACK");
            this.put("power", "ARROW_DAMAGE");
            this.put("respiration", "OXYGEN");
            this.put("aqua affinity", "WATER_WORKER");
            this.put("feather falling", "PROTECTION_FALL");
            this.put("thorns", "THORNS");
            this.put("protection", "PROTECTION_ENVIRONMENTAL");
            this.put("fire protection", "PROTECTION_FIRE");
            this.put("blast protection", "PROTECTION_EXPLOSIONS");
            this.put("projectile protection", "PROTECTION_PROJECTILE");
            this.put("efficiency", "DIG_SPEED");
            this.put("unbreaking", "DURABILITY");
            this.put("fortune", "LOOT_BONUS_BLOCKS");
            this.put("silk touch", "SILK_TOUCH");
        }
    };
    private static final Hashtable<String, String> differentNames = new Hashtable<String, String>() {
        {
            this.put("Birch Wood Stairs", "Wooden Stairs");
            this.put("Book And Quill", "Book and Quill");
            this.put("Cobble Wall", "Cobblestone Wall");
            this.put("Command", "Command Block");
            this.put("Cooked Beef", "Steak");
            this.put("Daylight Detector", "Daylight Sensor");
            this.put("Dead Bush", "Tall Grass");
            this.put("Diamond Spade", "Diamond Shovel");
            this.put("Diode", "Redstone Repeater");
            this.put("Exp Bottle", "Bottle of Enchanting");
            this.put("Explosive Minecart", "TNT Minecart");
            this.put("Gold Spade", "Gold Shovel");
            this.put("Gold Record", "Music Disk");
            this.put("Green Record", "Music Disk");
            this.put("Grilled Pork", "Cooked Porkchop");
            this.put("Iron Spade", "Iron Shovel");
            this.put("Jack O Lantern", "Jack-O-Lantern");
            this.put("Jungle Wood Stairs", "Wooden Stairs");
            this.put("Lapis Block", "Lapis Lazuli Block");
            this.put("Lapis Ore", "Lapis Lazuli Ore");
            this.put("Leather Chestplate", "Leather Tunic");
            this.put("Leather Helmet", "Leather Cap");
            this.put("Leather Leggings", "Leather Pants");
            this.put("Long Grass", "Tall Grass");
            this.put("Mycel", "Mycelium");
            this.put("Nether Fence", "Nether Brick Fence");
            this.put("Nether Warts", "Nether Wart");
            this.put("Pork", "Raw Porkchop");
            this.put("Record 3", "Music Disk");
            this.put("Record 4", "Music Disk");
            this.put("Record 5", "Music Disk");
            this.put("Record 6", "Music Disk");
            this.put("Record 7", "Music Disk");
            this.put("Record 8", "Music Disk");
            this.put("Record 9", "Music Disk");
            this.put("Record 10", "Music Disk");
            this.put("Record 11", "Music Disk");
            this.put("Record 12", "Music Disk");
            this.put("Red Rose", "Rose");
            this.put("Smooth Stairs", "Stone Brick Stairs");
            this.put("Speckled Melon", "Glistering Melon");
            this.put("Sprue Wood Stairs", "Wooden Stairs");
            this.put("Stone Plate", "Stone Pressure Plate");
            this.put("Stone Spade", "Stone Shovel");
            this.put("Sulphur", "Gunpowder");
            this.put("Thin Glass", "Glass Pane");
            this.put("Tnt", "TNT");
            this.put("Water Lily", "Lily Pad");
            this.put("Wood", "Wooden Plank");
            this.put("Wood Axe", "Wooden Axe");
            this.put("Wood Button", "Button");
            this.put("Wood Double Step", "Wooden Slab");
            this.put("Wood Hoe", "Wooden Hoe");
            this.put("Wood Pickaxe", "Wooden Pickaxe");
            this.put("Wood Plate", "Wooden Pressure Plate");
            this.put("Wood Spade", "Wooden Shovel");
            this.put("Wood Stairs", "Wooden Stairs");
            this.put("Wood Sword", "Wooden Sword");
            this.put("Yellow Flower", "Dandelion");
        }
    };

    public ENameParser() {
    }

    public static String parseName(String lore) {
        if (!lore.contains(" ")) {
            return null;
        } else {
            String[] pieces = lore.split(" ");
            String name = "";

            for(int i = 0; i < pieces.length - 1; ++i) {
                name = name + pieces[i] + (i < pieces.length - 2 ? " " : "");
            }

            name = ChatColor.stripColor(name);
            return name;
        }
    }

    public static int parseLevel(String lore) {
        if (!lore.contains(" ")) {
            return 0;
        } else {
            String[] pieces = lore.split(" ");
            return pieces.length <= 1 ? 0 : ERomanNumeral.getValueOf(pieces[pieces.length - 1]);
        }
    }

    public static String getName(ItemStack item) {
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasEnchants()) {
                return null;
            }

            if (item.getItemMeta().hasDisplayName()) {
                return null;
            }
        }

        return getName(item.getType());
    }

    public static String getName(Material item) {
        String name = item.name().toLowerCase();
        String[] pieces = name.split("_");
        name = "";

        for(int i = 0; i < pieces.length; ++i) {
            name = name + pieces[i].substring(0, 1).toUpperCase() + pieces[i].substring(1);
            if (i < pieces.length - 1) {
                name = name + " ";
            }
        }

        if (differentNames.containsKey(name)) {
            return ChatColor.AQUA + (String)differentNames.get(name);
        } else {
            return ChatColor.AQUA + name;
        }
    }

    public static String getVanillaName(Enchantment enchant) {
        Iterator var1 = eNames.entrySet().iterator();

        Map.Entry entry;
        do {
            if (!var1.hasNext()) {
                return enchant.getName();
            }

            entry = (Map.Entry)var1.next();
        } while(!((String)entry.getValue()).equals(enchant.getName()));

        if (!((String)entry.getKey()).contains(" ")) {
            return ((String)entry.getKey()).substring(0, 1).toUpperCase() + ((String)entry.getKey()).substring(1);
        } else {
            String[] parts = ((String)entry.getKey()).split(" ");
            String result = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);

            for(int i = 1; i < parts.length; ++i) {
                result = result + " " + parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
            }

            return result;
        }
    }

    public static String getBukkitName(String name) {
        return eNames.containsKey(name.toLowerCase()) ? (String)eNames.get(name.toLowerCase()) : name;
    }
}
