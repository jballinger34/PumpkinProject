//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.enchanting;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.service.MaterialClass;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class VanillaEnchantment extends CustomEnchantment {
    Enchantment vanilla;
    Map<MaterialClass, Integer> enchantability;

    public VanillaEnchantment(Enchantment vanilla, Material[] items, String group, String[] suffixGroup, int weight, double base, double interval, int max, String name) {
        super(name, items, group, weight);
        this.base = base;
        this.interval = interval;
        this.max = max;
        this.vanilla = vanilla;
        this.weight = new HashMap();
        this.weight.put(MaterialClass.DEFAULT, weight);
        this.enchantability = new HashMap();
        String[] var12 = suffixGroup;
        int var13 = suffixGroup.length;

        for(int var14 = 0; var14 < var13; ++var14) {
            String sGroup = var12[var14];
            this.suffixGroups.add(sGroup);
        }

    }

    public Enchantment getVanillaEnchant() {
        return this.vanilla;
    }

    public String name() {
        return this.vanilla.getName();
    }

    public boolean isTableEnabled() {
        return this.isTableEnabled && ((RootConfig)((EnchantmentAPI)Bukkit.getPluginManager().getPlugin("EnchantmentAPI")).getModuleForClass(RootConfig.class)).getBoolean(RootNode.VANILLA_TABLE);
    }

    public ItemStack addToItem(ItemStack item, int level) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
            if (meta.hasStoredEnchant(this.vanilla)) {
                if (meta.getEnchantLevel(this.vanilla) >= level) {
                    return item;
                }

                meta.removeStoredEnchant(this.vanilla);
                item.setItemMeta(meta);
            }
        }

        item.addUnsafeEnchantment(this.vanilla, level);
        return item;
    }

    public ItemStack removeFromItem(ItemStack item) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
            if (meta.hasStoredEnchant(this.vanilla)) {
                meta.removeStoredEnchant(this.vanilla);
                item.setItemMeta(meta);
                return item;
            }
        }

        item.removeEnchantment(this.vanilla);
        return item;
    }
}
