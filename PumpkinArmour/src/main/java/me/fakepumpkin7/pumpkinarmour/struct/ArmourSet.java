package me.fakepumpkin7.pumpkinarmour.struct;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ArmourSet {

    protected String id;

    protected List<ItemStack> pieces = new ArrayList<>();

    public ArmourSet(String id){
        this.id = id;
        initSet();
    }


    public void enableSetBonus(Player player){
    }
    public void disableSetBonus(Player player){
    }

    public String getId() {
        return id;
    }
    public void initSet(){
    }

    public void giveSetToPlayer(Player player){
        for(ItemStack piece : pieces){
            player.getInventory().addItem(piece);
        }
    }




}
