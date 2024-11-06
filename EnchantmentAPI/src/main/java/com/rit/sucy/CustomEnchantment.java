//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.PostToolEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ERomanNumeral;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomEnchantment implements Comparable<CustomEnchantment> {
    protected final String enchantName;
    protected String description;
    protected Material[] naturalItems;
    protected int max;
    protected ItemRarity rarity;
    protected boolean stacks;

    public CustomEnchantment(String name, String description, Material[] naturalItems, int max, boolean stacks) {
        this.enchantName = name;
        this.description = description;
        this.naturalItems = naturalItems;

        this.max = max;
        this.stacks = stacks;
    }

    public String name() {
        return this.enchantName;
    }

    public String getDescription() {
        return this.description;
    }
    public ItemRarity getRarity(){return this.rarity;}
    public void setRarity(ItemRarity rarity){this.rarity = rarity;}
    public boolean canStack(){
        return stacks;
    }


    public int getMaxLevel() {
        return this.max;
    }

    public void setMaxLevel(int value) {
        this.max = value;
    }


    public void setNaturalMaterials(Material[] materials) {
        this.naturalItems = materials;
    }

    /** @deprecated */
    public String[] getNaturalItems() {
        String[] natItems = new String[this.naturalItems.length];

        for(int i = 0; i < this.naturalItems.length; ++i) {
            natItems[i] = this.naturalItems[i].name();
        }

        return natItems;
    }

    public Material[] getNaturalMaterials() {
        return this.naturalItems;
    }


    public boolean canEnchantOnto(ItemStack item) {
        if (this.naturalItems != null && item != null) {
            Material[] var2 = this.naturalItems;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Material validItem = var2[var4];
                if (item.getType() == validItem) {
                    return true;
                }
            }
        }
        return false;
    }



    public ItemStack addToItem(ItemStack item, int enchantLevel) {
        Validate.notNull(item);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getServer().getItemFactory().getItemMeta(item.getType());
        }

        this.addToItem(item.getType(), meta, enchantLevel);
        item.setItemMeta(meta);
        return item;
    }

    public ItemMeta addToItem(Material material, ItemMeta meta, int enchantLevel) {
        Validate.notNull(meta);
        List<String> metaLore = meta.getLore() == null ? new ArrayList() : meta.getLore();
        String thisName = this.name();
        ChatColor enchantmentColour = this.getRarity().getColor();
        Iterator var7 = EnchantmentAPI.getEnchantments(meta).entrySet().iterator();

        while(var7.hasNext()) {
            Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var7.next();
            if (((CustomEnchantment)entry.getKey()).name().equals(thisName)) {
                if ((Integer)entry.getValue() >= enchantLevel) {
                    return meta;
                }

                ((List)metaLore).remove(enchantmentColour + thisName + " " + ERomanNumeral.numeralOf((Integer)entry.getValue()));
            }
        }
        int index = 0;
        for(int i = 0; i < metaLore.size(); i++){
            if(metaLore.get(i).equals("")){
                index = i; break;
            }
        }

        ((List)metaLore).add(index, enchantmentColour + this.enchantName + " " + ERomanNumeral.numeralOf(enchantLevel));
        meta.setLore((List)metaLore);
        if (!meta.hasDisplayName() || meta.getDisplayName() == null) {
            meta.setDisplayName(ENameParser.getName(material));
        }

        return meta;
    }

    public ItemStack removeFromItem(ItemStack item) {
        if (!item.hasItemMeta()) {
            return item;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (!meta.hasLore()) {
                return item;
            } else {
                List<String> metaLore = meta.getLore();
                boolean containedEnchantment = false;
                Iterator var5 = EnchantmentAPI.getEnchantments(item).entrySet().iterator();

                while(var5.hasNext()) {
                    Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var5.next();
                    if (((CustomEnchantment)entry.getKey()).name().equals(this.name())) {
                        metaLore.remove("§7" + this.name() + " " + ERomanNumeral.numeralOf((Integer)entry.getValue()));
                        containedEnchantment = true;
                    }
                }

                if (containedEnchantment) {
                    meta.setLore(metaLore);
                    item.setItemMeta(meta);
                }

                return item;
            }
        }
    }

    public boolean equals(Object obj) {
        return obj instanceof CustomEnchantment && this.name().equalsIgnoreCase(((CustomEnchantment)obj).name());
    }

    public int compareTo(CustomEnchantment customEnchantment) {
        return this.name().compareTo(customEnchantment.name());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, CustomDamageEvent event) {
    }

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, CustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
    }

    public boolean applyToolEffect(Player player, Block block, List<ItemStack> drops, TreeMultiMap<PostToolEffectRunnable> postRunTasks, int enchantLevel, BlockEvent event) {
        return false;
    }

    public void applyMiscEffect(Player player, int enchantLevel, PlayerInteractEvent event) {
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
    }

    public void applyEntityEffect(Player player, int enchantLevel, PlayerInteractEntityEvent event) {
    }

    public void applyProjectileEffect(LivingEntity user, int enchantLevel, ProjectileLaunchEvent event) {
    }
}
