//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.service;

import org.bukkit.Material;

public enum MaterialClass {
    WOOD(15),
    STONE(5),
    IRON(14),
    GOLD(25),
    DIAMOND(10),
    LEATHER(15),
    CHAIN(12),
    DEFAULT(10);

    private final int enchantability;

    private MaterialClass(int enchantabilty) {
        this.enchantability = enchantabilty;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public static int getEnchantabilityFor(Material material) {
        int enchantability = DEFAULT.getEnchantability();
        MaterialClass[] var2 = values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            MaterialClass materialClass = var2[var4];
            if (material.name().contains(materialClass.name() + "_")) {
                enchantability = materialClass.getEnchantability();
                break;
            }
        }

        return enchantability;
    }
}
