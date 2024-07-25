package me.fakepumpkin7.pumpkincrates.crates;

import lombok.Getter;
import me.fakepumpkin7.pumpkincrates.struct.Reward;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.fakepumpkin7.pumpkincrates.PumpkinCrates.CRATE_ID;

public class Crate {

    private static Random random = new Random();

    private String name;

    @Getter
    private String id;

    protected ItemRarity rarity;


    //these are the defaults
    protected int minDrops = 4;
    protected int maxDrops = 4;
    protected List<Reward> rewards = new ArrayList<>();

    public Crate(String name, String id, ItemRarity rarity){
        this.name = name;
        this.id = id;

        this.rarity = rarity;
    }



    protected void rollCrateRewards(){
    }
    public ItemStack generateCrate(){
        ItemStack crateItem = new ItemBuilder(Material.CHEST)
                .setName(rarity.getColor() + name)
                .addNBT(CRATE_ID, id)
                .addRarityLore(rarity)
                .build();
        return crateItem;
    }
    public void openCrate(Player player){
        //finds out how many rolls
        int rolls = random.nextInt( 1 + maxDrops - minDrops) + minDrops;
        //array for drops
        ArrayList<ItemStack> drops = new ArrayList<>();

        //sum up total weight
        int totalWeight = 0;
        for(Reward reward : rewards){
            totalWeight += reward.getWeight();
        }

        //for each roll, get a random weight somewhere between 0 and totalWeight
        //this random weight will align with a specific item
        //add this item to drops array.

        for(int i = 0; i < rolls; i++){
            //rolls the crates rewards every time a drop is opened
            //(so stuff like random enchant changes up)
            rollCrateRewards();




            double randomWeight = Math.random() * totalWeight;


            for (Reward reward : rewards) {


                if(reward.getWeight() <= 0) continue;
                randomWeight -= reward.getWeight();
                if (randomWeight <= 0) {
                    drops.add(reward.getItemStack());
                    break;
                }

            }

        }

        //give items to player
        if(drops.isEmpty()){
            return;
        }
        PlayerUtils.addItems(player, drops);
        ChatUtils.success(player, "Opened " + name);


    }
}
