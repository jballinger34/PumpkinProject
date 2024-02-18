package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;


public class OnJoinTest implements Listener {

    private ItemStack item;


    public OnJoinTest(ItemStack item){
        this.item = item;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.getInventory().addItem(item);

    }

}
