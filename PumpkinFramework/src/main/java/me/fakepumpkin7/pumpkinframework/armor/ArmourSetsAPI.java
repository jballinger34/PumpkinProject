package me.fakepumpkin7.pumpkinframework.armor;

import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public interface ArmourSetsAPI {
    List<ItemStack> getArmourSet();
    List<ItemStack> getArmourSet(String id);
    List<ItemStack> getArmourSet(ItemRarity rarity);

}
