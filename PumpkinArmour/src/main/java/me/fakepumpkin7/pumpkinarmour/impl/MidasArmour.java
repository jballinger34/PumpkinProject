package me.fakepumpkin7.pumpkinarmour.impl;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MidasArmour extends ArmourSet {

    public MidasArmour(){
        super("midas-armour");
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
                .addNBT("pumpkin-armour-id", id)
                .setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Midas Helmet")
                .setDefence(4)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_CHESTPLATE)
                .addNBT("pumpkin-armour-id", id)
                .setName(ChatColor.GREEN + "Emerald Chestplate")
                .setDefence(7)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_LEGGINGS)
                .addNBT("pumpkin-armour-id", id)
                .setName(ChatColor.GREEN + "Emerald Leggings")
                .setDefence(6)
                .build());

        pieces.add(new ItemBuilder(Material.GOLD_BOOTS)
                .addNBT("pumpkin-armour-id", id)
                .setName(ChatColor.GREEN + "Emerald Boots")
                .setDefence(3)
                .build());
    }
}
