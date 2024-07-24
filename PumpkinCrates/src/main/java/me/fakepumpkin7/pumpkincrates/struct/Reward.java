package me.fakepumpkin7.pumpkincrates.struct;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Reward {

    @Getter
    private ItemStack itemStack;

    @Getter
    private double weight;

    public Reward(ItemStack itemStack, double weight){
        this.itemStack = itemStack;
        this.weight = weight;
    }


}
