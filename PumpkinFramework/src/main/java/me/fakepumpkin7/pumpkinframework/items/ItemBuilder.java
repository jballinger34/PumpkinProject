package me.fakepumpkin7.pumpkinframework.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    ItemStack item;

    public ItemBuilder(Material material){
        if(material == Material.SKULL_ITEM){
            this.item = new ItemStack(material, 1, (short) 3);
        } else {
            this.item = new ItemStack(material);
        }

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
    private ItemBuilder addStatsLore(){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore == null){
            lore = new ArrayList<>();
        }
        List<String> statsLore = generateStatsLore();

        statsLore.addAll(lore);
        meta.setLore(statsLore);
        item.setItemMeta(meta);
        return this;
    }
    private List<String> generateStatsLore(){
        List<String> lore = new ArrayList<>();

        double baseDamage = CombatUtils.getItemBaseDamage(item);
        double defence = CombatUtils.getItemDefence(item);
        double maxHealth = CombatUtils.getItemMaxHealth(item);
        double speed = CombatUtils.getItemSpeed(item);

        double damageMulti = CombatUtils.getItemDamageMulti(item);
        double knockback = CombatUtils.getItemKnockbackMulti(item);

        if(baseDamage != 0){
            lore.add(ChatColor.GRAY + "Damage: "+ ChatColor.RED +"+" + baseDamage);
        }
        if(defence != 0){
            lore.add(ChatColor.GRAY + "Defence: "+ ChatColor.RED +"+"  + defence);
        }
        if(maxHealth != 0){
            lore.add(ChatColor.GRAY + "Health: "+ ChatColor.RED  +"+" + maxHealth);
        }
        if(speed != 0){
            lore.add(ChatColor.GRAY + "Speed: "+ ChatColor.RED  +"+"+ speed);
        }

        //for the multis, 1 is default
        if(damageMulti != 1){
            lore.add(ChatColor.GRAY + "All damage dealt is multiplied by " + ChatColor.RED + damageMulti);
        }
        if(knockback != 1){
            lore.add(ChatColor.GRAY + "All knockback dealt is multiplied by " + ChatColor.RED +  knockback);
        }
        if(lore.size() > 0){
            lore.add("");
        }



        return lore;
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
    public ItemBuilder addBaseDamage(double baseDamage){
        double currentBaseDamage = NbtUtil.getNbtDouble(item, "pumpkin-base-damage");
        setBaseDamage(currentBaseDamage + baseDamage);
        return this;
    }

    public ItemBuilder setDamageMulti(double damageMulti){
        addNBT("pumpkin-damage-multi", damageMulti);
        return this;
    }
    public ItemBuilder addDamageMulti(double damageMulti){
        double currentDamageMulti = NbtUtil.getNbtDouble(item, "pumpkin-damage-multi");
        setDamageMulti(currentDamageMulti + damageMulti);
        return this;
    }

    public ItemBuilder setKnockback(double knockback){
        addNBT("pumpkin-knockback-multi", knockback);
        return this;
    }
    public ItemBuilder addKnockback(double knockback){
        double currentKnockback = NbtUtil.getNbtDouble(item, "pumpkin-knockback-multi");
        setKnockback(currentKnockback + knockback);
        return this;
    }

    public ItemBuilder setDefence(double defence){
        addNBT("pumpkin-custom-defence", defence);
        return this;
    }
    public ItemBuilder addDefence(double defence){
        double currentDefence = NbtUtil.getNbtDouble(item, "pumpkin-custom-defence");
        setDefence(currentDefence + defence);
        return this;
    }

    public ItemBuilder setHealth(double health){
        addNBT("pumpkin-custom-health", health);
        return this;
    }
    public ItemBuilder addHealth(double health){
        double currentHealth = NbtUtil.getNbtDouble(item, "pumpkin-custom-health");
        setHealth(currentHealth + health);
        return this;
    }


    public ItemBuilder setSpeed(double speed){
        addNBT("pumpkin-custom-speed", speed);
        return this;
    }
    public ItemBuilder addSpeed(double speed){
        double currentSpeed = NbtUtil.getNbtDouble(item, "pumpkin-custom-speed");
        setSpeed(currentSpeed + speed);
        return this;
    }

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

    public ItemBuilder dyeLeather(Color color){
        if(!item.hasItemMeta() || !(item.getItemMeta() instanceof LeatherArmorMeta)) return this;

        LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
        lam.setColor(color);

        item.setItemMeta(lam);

        return this;
    }

    public ItemBuilder skullTexture(String skullURL){
        if(!item.hasItemMeta() || !(item.getItemMeta() instanceof SkullMeta)) return this;
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();


        String skullTexture = Base64.getEncoder().encodeToString(("{textures:{SKIN:{url:\"" + skullURL + "\"}}}").getBytes());


        GameProfile skinProfile = new GameProfile(UUID.randomUUID(), (String)null);
        skinProfile.getProperties().put("textures", new Property("textures", skullTexture, "signed"));

        try {
            Field profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, skinProfile);
        } catch (NoSuchFieldException | IllegalAccessException var17) {
            var17.printStackTrace();
        }


        item.setItemMeta(itemMeta);
        return this;
    }


    public ItemStack build(){
        //could have this as a public method,
        // but like this it will be called for every item built with itembuilder
        addStatsLore();
        return this.item;
    }


}
