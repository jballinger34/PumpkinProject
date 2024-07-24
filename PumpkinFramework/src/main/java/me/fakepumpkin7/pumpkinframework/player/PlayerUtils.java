package me.fakepumpkin7.pumpkinframework.player;

import me.fakepumpkin7.pumpkinframework.items.ItemUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PlayerUtils {

    public static void addItems(Player player, ItemStack item){
        Collection<ItemStack> items = new ArrayList<>();
        items.add(item);

        addItems(player, items);
    }
    public static void addItems(Player player, Collection<ItemStack> items){
        Location location = player.getLocation();
        for(ItemStack item : items){

            if(!ItemUtil.isValid(item)){
                continue;
            }



            HashMap<Integer, ItemStack> couldntAdd = player.getInventory().addItem(item);
            for(Integer i : couldntAdd.keySet()){
                location.getWorld().dropItem(location, couldntAdd.get(i));
            }
        }
    }
}
