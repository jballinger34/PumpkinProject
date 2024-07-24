package me.fakepumpkin7.pumpkincrates.listeners;

import me.fakepumpkin7.pumpkincrates.CrateRegistry;
import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static me.fakepumpkin7.pumpkincrates.PumpkinCrates.CRATE_ID;

public class CrateListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null || item.getAmount() == 0 || item.getType() == Material.AIR) return;
        if(!NbtUtil.hasNbt(item, CRATE_ID)) return;
        //we know its a crate now
        event.setCancelled(true);

        Player player = event.getPlayer();

        //find which crate it is
        String id = NbtUtil.getNbtString(item, CRATE_ID);
        CrateRegistry crateRegistry = CrateRegistry.getCrateById(id);
        if(crateRegistry == null) {
            ChatUtils.warn(player, "This crate has invalid id");
            return;
        }
        Crate crate = crateRegistry.getCrate();

        //open crate

        item.setAmount(item.getAmount() - 1);
        player.setItemInHand(item);

        crate.openCrate(player);


    }
}
