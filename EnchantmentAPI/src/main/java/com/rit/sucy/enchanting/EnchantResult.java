//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.enchanting;

import com.rit.sucy.CustomEnchantment;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class EnchantResult {
    private ItemStack item;
    private int level = -1;
    private CustomEnchantment firstEnchant;
    Map<CustomEnchantment, Integer> addedEnchants;

    public EnchantResult() {
    }

    public int getLevel() {
        return this.level;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public CustomEnchantment getFirstEnchant() {
        return this.firstEnchant;
    }

    public Map<CustomEnchantment, Integer> getAddedEnchants() {
        return this.addedEnchants;
    }

    public void setLevel(int value) {
        if (this.level == -1) {
            this.level = value;
        }

    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setFirstEnchant(CustomEnchantment enchant) {
        if (this.firstEnchant == null) {
            this.firstEnchant = enchant;
        }

    }

    public void setAddedEnchants(Map<CustomEnchantment, Integer> enchants) {
        this.addedEnchants = enchants;
    }
}
