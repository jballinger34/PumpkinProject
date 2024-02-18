package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;


import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
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

        ItemStack item2 = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDefence(1000).setSpeed(0.3).setHealth(80).addGlow().setName("TEST").build();
        p.getInventory().addItem(item2);

    }

}
