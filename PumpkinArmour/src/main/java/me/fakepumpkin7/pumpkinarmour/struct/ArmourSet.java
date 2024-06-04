package me.fakepumpkin7.pumpkinarmour.struct;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class ArmourSet {
    static HashMap<UUID, String> uuidToSetBonusId = new HashMap<>();

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
