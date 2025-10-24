package me.fakepumpkin7.pumpkincrates.struct;

import org.bukkit.inventory.ItemStack;

public class Reward {

    private ItemStack itemStack;

    private double weight;

    public Reward(ItemStack itemStack, double weight){
        this.itemStack = itemStack;
        this.weight = weight;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getWeight() {
        return weight;
    }
}
