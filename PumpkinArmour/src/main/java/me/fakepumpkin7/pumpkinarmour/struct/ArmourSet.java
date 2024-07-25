package me.fakepumpkin7.pumpkinarmour.struct;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
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

    protected ItemRarity rarity;

    public ArmourSet(String id, ItemRarity rarity){
        this.id = id;
        this.rarity = rarity;
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

    public ItemRarity getRarity() {
        return rarity;
    }

    public void giveSetToPlayer(Player player){
        for(ItemStack piece : pieces){
            player.getInventory().addItem(piece);
        }
    }

    public List<ItemStack> getPieces(){
        return pieces;
    }




}
