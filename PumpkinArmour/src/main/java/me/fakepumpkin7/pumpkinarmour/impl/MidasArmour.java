package me.fakepumpkin7.pumpkinarmour.impl;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static me.fakepumpkin7.pumpkinarmour.PumpkinArmour.PUMPKIN_ARMOUR_ID;

public class MidasArmour extends ArmourSet {

    public MidasArmour(){
        super("midas-armour", ItemRarity.MYTHIC);
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
        pieces.add(new ItemBuilder(Material.GOLD_HELMET)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Midas Helmet")
                .setDefence(6)
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_CHESTPLATE)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Midas Chestplate")
                .setDefence(10)
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_LEGGINGS)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Midas Leggings")
                .setDefence(9)
                .addRarityLore(rarity)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_BOOTS)
                .addNBT(PUMPKIN_ARMOUR_ID, id)
                .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Midas Boots")
                .setDefence(6)
                .addRarityLore(rarity)
                .build());
    }
}
