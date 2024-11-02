package me.fakepumpkin7.pumpkinarmour.impl;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantManager;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.fakepumpkin7.pumpkinarmour.PumpkinArmour.PUMPKIN_ARMOUR_ID;

public class EmeraldArmour extends ArmourSet {

    public EmeraldArmour(){
        super("emerald-armour-set", ItemRarity.RARE);
        //super will call init set, which will put set into pieces array
    }

    @Override
    public void enableSetBonus(Player player) {
    }

    @Override
    public void disableSetBonus(Player player) {
    }

    @Override
    public void initSet(){

        pieces.add(new ItemBuilder(Material.SKULL_ITEM)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                //add a random uuid otherwise this item will be stackable as its a skull
                .addNBT("uuid", UUID.randomUUID().toString())
                .setName(ChatColor.GREEN + "Emerald Helmet")
                .setDefence(4)
                .skullTexture("http://textures.minecraft.net/texture/8926c1f2c3c14d086c40cfc235fe938694f4a51067ada4726b486ea1c87b03e2")
                .addStatsLore()
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GREEN + "Emerald Chestplate")
                .setDefence(7)
                .dyeLeather(Color.LIME)
                .addStatsLore()
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.LEATHER_LEGGINGS)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GREEN + "Emerald Leggings")
                .setDefence(6)
                .dyeLeather(Color.LIME)
                .addStatsLore()
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.LEATHER_BOOTS)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GREEN + "Emerald Boots")
                .setDefence(3)
                .dyeLeather(Color.LIME)
                .addStatsLore()
                .addRarityLore(rarity)
                .build());
    }
}
