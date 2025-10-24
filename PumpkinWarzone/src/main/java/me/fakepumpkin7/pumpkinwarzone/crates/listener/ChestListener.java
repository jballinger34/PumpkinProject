package me.fakepumpkin7.pumpkinwarzone.crates.listener;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import me.fakepumpkin7.pumpkinframework.lootcrates.LootCrateManager;
import me.fakepumpkin7.pumpkinframework.particle.ParticleEffect;
import me.fakepumpkin7.pumpkinwarzone.crates.tasks.WashupTask;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestListener implements Listener {
    public static Map<Location, Long> chestAccessHistory;

    private final Random random = new Random();
    public final int minChestResetMins = 4;
    public final int maxChestResetMins = 8;

    public ChestListener() {
        chestAccessHistory = new HashMap<>();
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.CHEST){
            Location chestLocation = event.getClickedBlock().getLocation();
            if(chestAccessHistory.containsKey(chestLocation)){
                //check if on cooldown
                if(System.currentTimeMillis() < chestAccessHistory.get(chestLocation)){
                    long secondsTillNextRestock = (chestAccessHistory.get(chestLocation) - System.currentTimeMillis())/1000 ;
                    long minsTillNextRestock = secondsTillNextRestock / 60;
                    if(secondsTillNextRestock < 60){
                        ChatUtils.info(event.getPlayer(), "This chest will restock in "+ secondsTillNextRestock +" seconds!");
                    } else {
                        ChatUtils.info(event.getPlayer(), "This chest will restock in " + minsTillNextRestock + " minutes!");
                    }
                    return;
                }
                //if off cd, get random rarity, populate chest, set new cooldown.
                ItemRarity rarity = ItemRarity.getRandomChestRarityWeighted();

                //below is code for when the wash-up is active, we will have a washup task that
                // sets a boolean value to true when washup active, if a chest is opened when
                // washup, the rarities will be bumped up
                if(WashupTask.washupActive){
                    if(rarity == ItemRarity.RARE){
                        rarity = ItemRarity.MYTHIC;
                    } else if (rarity == ItemRarity.COMMON || rarity == ItemRarity.UNCOMMON){
                        rarity = ItemRarity.RARE;
                    }
                }



                chestAccessHistory.put(chestLocation,System.currentTimeMillis() + (random.nextInt(maxChestResetMins-minChestResetMins) + minChestResetMins) * 60L * 1000L);

                ParticleEffect.sendToPlayer(ParticleEffect.SPELL,event.getPlayer(),chestLocation.clone().add(0.5,0.5,0.5),0,0,0,0.4F,40);
                ParticleEffect.sendToPlayer(ParticleEffect.RED_DUST,event.getPlayer(),chestLocation.clone().add(0.5,0.5,0.5),0,0,0,0.25F,35);
                ParticleEffect.launchFirework(chestLocation.clone().add(0,1,0), getFireworkColour(rarity));

                ItemStack[] loot = LootCrateManager.getLootCrateAPI().getCrateRewards(rarity);
                ((Chest) event.getClickedBlock().getState()).getInventory().setContents(loot);



            }


        }
    }
    private Color getFireworkColour(ItemRarity rarity){
        Color c = Color.WHITE;
        if(rarity == ItemRarity.UNCOMMON){
            c = Color.GREEN;
        } else if (rarity == ItemRarity.RARE) {
            c = Color.AQUA;
        } else if (rarity == ItemRarity.MYTHIC) {
            c = Color.YELLOW;
        } else if (rarity == ItemRarity.HEROIC) {
            c = Color.RED;
        }
        return c;
    }
}
