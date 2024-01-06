package me.fakepumpkin7.pumpkinframework.items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    ItemStack item;

    public ItemBuilder(Material material){
        this.item = new ItemStack(material);
    }
    public ItemBuilder(ItemStack itemStack){this.item = itemStack;}

    public ItemBuilder setName(String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreLine(String toAdd){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore == null){
            lore = new ArrayList<>();
        }
        lore.add(toAdd);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addGlow(){
        if(item.getType() == Material.FISHING_ROD){
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        } else {
            item.addUnsafeEnchantment(Enchantment.LURE, 1);
        }
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBaseDamage(double baseDamage){
        addNBT("pumpkin-base-damage", baseDamage);
        return this;
    }

    public ItemBuilder setDamageMulti(double damageMulti){
        addNBT("pumpkin-damage-multi", damageMulti);
        return this;
    }

    public ItemBuilder setKnockback(double knockback){
        addNBT("pumpkin-knockback-multi", knockback);
        return this;
    }

    public ItemBuilder setDefence(double defence){
        addNBT("pumpkin-custom-defence", defence);
        return this;
    }

    public ItemBuilder setHealth(double health){
        addNBT("pumpkin-custom-health", health);
        return this;
    }

    public ItemBuilder setSpeed(double speed){
        addNBT("pumpkin-custom-speed", speed);
        return this;
    }

    //TODO
    // ADD METHODS TO ADD SPEED, DEFENCE, HEALTH

    public ItemBuilder addNBT(String key, double doubleVal){
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        itemCompound.setDouble(key, doubleVal);

        nmsItem.setTag(itemCompound);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        return this;
    }
    public ItemBuilder addNBT(String key, int integer){
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        itemCompound.setInt(key, integer);

        nmsItem.setTag(itemCompound);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        return this;
    }

    public ItemBuilder addNBT(String key, String str){
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        itemCompound.setString(key, str);

        nmsItem.setTag(itemCompound);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        return this;
    }




    public ItemStack build(){
        return this.item;
    }


}
