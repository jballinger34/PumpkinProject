package me.fakepumpkin7.pumpkincore.patches;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class DisableDefaultEnchanting implements Listener {

    @EventHandler
    public void onOpenInv(InventoryOpenEvent event){
        Inventory inv = event.getInventory();
        if(inv.getType().equals(InventoryType.ENCHANTING) || inv.getType().equals(InventoryType.ANVIL)){
            Player p = (Player) event.getPlayer();
            ChatUtils.info(p, "Default enchanting is disabled, run /enchants for more info.");
            event.setCancelled(true);
        }
    }





}
