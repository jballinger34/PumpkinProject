package me.fakepumpkin7.pumpkinarmour;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import me.fakepumpkin7.pumpkinarmour.struct.ArmourSetRegistry;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArmourSetsAPI implements me.fakepumpkin7.pumpkinframework.armor.ArmourSetsAPI {

    private static ArmourSetsAPI instance = new ArmourSetsAPI();
    private Random random = new Random();

    public static ArmourSetsAPI getInstance() {
        return instance;
    }


    @Override
    public List<ItemStack> getArmourSet() {
        List<ArmourSetRegistry> allSets = Arrays.asList(ArmourSetRegistry.values());
        if (allSets.isEmpty()) {
            return null;
        }

        return allSets.get(random.nextInt(allSets.size())).getSet().getPieces();
    }

    @Override
    public List<ItemStack> getArmourSet(String id) {
        if(ArmourSetRegistry.getSetById(id) == null){
            System.out.println("No set with this id");
            return null;
        }
        return ArmourSetRegistry.getSetById(id).getPieces();
    }

    @Override
    public List<ItemStack> getArmourSet(ItemRarity itemRarity) {
        List<ArmourSetRegistry> allSets = Arrays.asList(ArmourSetRegistry.values());
        if (allSets.isEmpty()) {
            return null;
        }
        List<ArmourSet> setsOfRarity = new ArrayList<>();
        for(ArmourSetRegistry set : allSets){
            if(set.getSet().getRarity() == itemRarity){
                setsOfRarity.add(set.getSet());
            }
        }
        if(setsOfRarity.isEmpty()){
            return null;
        }

        return setsOfRarity.get(random.nextInt(allSets.size())).getPieces();

    }
}
