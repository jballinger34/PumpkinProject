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

        ShapedRecipe sr = new ShapedRecipe(item);
        sr.shape("  R",
                 "EEE",
                 "   ");
        sr.setIngredient('E', Material.EYE_OF_ENDER);
        sr.setIngredient('R', Material.YELLOW_FLOWER);

        Bukkit.addRecipe(sr);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.getInventory().addItem(item);

    }

}
