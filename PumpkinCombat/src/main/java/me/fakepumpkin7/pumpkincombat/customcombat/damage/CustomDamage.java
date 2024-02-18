package me.fakepumpkin7.pumpkincombat.customcombat.damage;


import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners.*;
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
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(this), plugin);

        this.plugin = plugin;

    }



}
