package me.fakepumpkin7.pumpkinarmour.impl;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EmeraldArmour extends ArmourSet {

    public EmeraldArmour(){
        super("emerald-armour-set");
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
        pieces.add(new ItemBuilder(Material.DIAMOND_HELMET)
                .addNBT("pumpkin-armour-id", id)
                .setName("Emerald Helmet")
                .setDefence(4)
                .build());

        pieces.add(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addNBT("pumpkin-armour-id", id)
                .setName("Emerald Chestplate")
                .setDefence(7)
                .build());

        pieces.add(new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addNBT("pumpkin-armour-id", id)
                .setName("Emerald Leggings")
                .setDefence(6)
                .build());

        pieces.add(new ItemBuilder(Material.DIAMOND_BOOTS)
                .addNBT("pumpkin-armour-id", id)
                .setName("Emerald Boots")
                .setDefence(3)
                .build());
    }
}
