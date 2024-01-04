package me.fakepumpkin7.pumpkincombat.customcombat.damage;


import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.CustomDamageListener;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.DamageListener;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.InitCustomDamageListener;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.OnJoinTest;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import de.tr7zw.nbtapi.NBTItem;

public class CustomDamage {

    PumpkinCombat plugin;

    public CustomDamage(PumpkinCombat plugin){
        Bukkit.getPluginManager().registerEvents(new DamageListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new CustomDamageListener(this), plugin);
        Bukkit.getPluginManager().registerEvents(new InitCustomDamageListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents
                (new OnJoinTest(new ItemBuilder(Material.DIAMOND_AXE).setName("TEST").addGlow().setBaseDamage(5).setKnockback(4000).build()), plugin);

        this.plugin = plugin;

    }

    public double getEntityBaseDamage(Entity entity){
        double base = 0;

        if(entity.hasMetadata("pumpkin-base-damage")){
            base = entity.getMetadata("pumpkin-base-damage").get(0).asDouble();
        }

        return base;
    }
    public double getEntityDamageMulti(Entity entity){
        double multi = 1;

        if(entity.hasMetadata("pumpkin-damage-multi")){
            multi = entity.getMetadata("pumpkin-damage-multi").get(0).asDouble();
        }

        return multi;
    }

    public void setEntityBaseDamage(Entity entity, double base){
        entity.setMetadata("pumpkin-base-damage", new FixedMetadataValue(plugin, base));
    }
    public void setEntityDamageMulti(Entity entity, double multi){
        entity.setMetadata("pumpkin-damage-multi", new FixedMetadataValue(plugin, multi));
    }

    public void addEntityBaseDamage(Entity entity, double toAdd){
        double current = getEntityBaseDamage(entity);

        double newVal = current + toAdd;

        setEntityBaseDamage(entity, newVal);
    }
    public void addEntityDamageMulti(Entity entity, double toAdd){
        double current = getEntityDamageMulti(entity);

        double newVal = current + toAdd;

        setEntityDamageMulti(entity, newVal);
    }


    public double getItemBaseDamage(ItemStack item) {
        double base = 0;
        if(item.getType().equals(Material.AIR) || item == null){
            return base;
        }

        if (NbtUtil.hasNbt(item,"pumpkin-base-damage")) {
            base = NbtUtil.getNbtDouble(item, "pumpkin-base-damage");
        }

        return base;

    }
    public double getItemDamageMulti(ItemStack item) {
        double multi = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return multi;
        }


        if (me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil.hasNbt(item,"pumpkin-damage-multi")) {
            multi = NbtUtil.getNbtDouble(item, "pumpkin-damage-multi");
        }

        return multi;
    }

    public double getItemKnockbackMulti(ItemStack item) {
        double knockbackMulti = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return knockbackMulti;
        }


        if (NbtUtil.hasNbt(item,"pumpkin-knockback-multi")) {
            knockbackMulti = NbtUtil.getNbtDouble(item, "pumpkin-knockback-multi");
        }

        return knockbackMulti;
    }
    public ItemStack setItemBaseDamage(ItemStack item, double base){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-base-damage", base);

        return nbtItem.getItem();

    }
    public ItemStack setItemDamageMulti(ItemStack item, double multi){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-damage-multi", multi);

        return nbtItem.getItem();
    }

    public ItemStack addItemBaseDamage(ItemStack item, double toAdd){
        double current = getItemBaseDamage(item);
        double newVal = current + toAdd;

        return setItemBaseDamage(item, newVal);
    }
    public ItemStack addItemDamageMulti(ItemStack item, double toAdd){
        double current = getItemDamageMulti(item);
        double newVal = current + toAdd;

       return setItemDamageMulti(item, newVal);
    }




}
