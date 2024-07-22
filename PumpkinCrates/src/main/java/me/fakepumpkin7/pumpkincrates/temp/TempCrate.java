package me.fakepumpkin7.pumpkincrates.temp;

import me.fakepumpkin7.pumpkinframework.armor.ArmourSetsAPI;
import me.fakepumpkin7.pumpkinframework.economy.Bank;
import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;

import static me.fakepumpkin7.pumpkincrates.PumpkinCrates.CRATE_ID;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class TempCrate implements Listener {

    ItemStack crate = new ItemBuilder(Material.CHEST).setName("Temp Crate").addNBT(CRATE_ID,"temp-crate").build();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null || item.getAmount() == 0 || item.getType() == Material.AIR) return;
        if(!NbtUtil.hasNbt(item, CRATE_ID)) return;
        //we know its a crate now
        event.setCancelled(true);


        Player player = event.getPlayer();
        if(NbtUtil.getNbtString(item, CRATE_ID).equalsIgnoreCase("temp-crate")){
            //remove 1 tempcrate
            item.setAmount(item.getAmount() - 1);
            player.setItemInHand(item);

            //open it
            openTemp(player);
        }
    }
    @EventHandler
    public void giveOnJoin(PlayerJoinEvent event){
        PlayerUtils.addItems(event.getPlayer(), crate);
    }


    public void openTemp(Player player){
        PlayerUtils.addItems(player, Arrays.asList(
                ArmourSetsAPI.getArmourItem(),
                EnchantAPI.getEnchant(),
                Bank.createMoneyNote("JAMIEEEEEE", 10000.0)));
    }


}
