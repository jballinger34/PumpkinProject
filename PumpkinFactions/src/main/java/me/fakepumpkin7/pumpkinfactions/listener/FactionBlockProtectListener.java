package me.fakepumpkin7.pumpkinfactions.listener;


import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class FactionBlockProtectListener implements Listener {

    //TODO stop players interacting with redstone and stuff in other facs chunks

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player breaker =  event.getPlayer();
        Block block = event.getBlock();

        if(breaker == null) return;
        if(block == null) return;


        Faction claimOwner = FactionHandler.getInstance().getClaimAt(new FChunk(block.getChunk()));
        if(claimOwner == null) return;
        if(claimOwner.equals(FactionHandler.getInstance().getPlayersFaction(breaker.getUniqueId()))){
            return;
        }

        event.setCancelled(true);
        ChatUtils.info(breaker, "You cannot break blocks in this chunk as it is claimed by another faction.");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player placer =  event.getPlayer();
        Block block = event.getBlock();
        
        if(placer == null) return;
        if(block == null) return;

        Faction claimOwner = FactionHandler.getInstance().getClaimAt(new FChunk(block.getChunk()));

        if(claimOwner == null) return;

        if(claimOwner.equals(FactionHandler.getInstance().getPlayersFaction(placer.getUniqueId()))){
            return;
        }

        event.setCancelled(true);
        ChatUtils.info(placer, "You cannot place blocks in this chunk as it is claimed by another faction.");
    }


    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event){
        Player placer =  event.getPlayer();
        Block block = event.getClickedBlock();

        if(placer == null) return;

        if(block == null) {
            return;
        }

        Faction claimOwner = FactionHandler.getInstance().getClaimAt(new FChunk(block.getChunk()));

        if(claimOwner == null) return;

        if(claimOwner.equals(FactionHandler.getInstance().getPlayersFaction(placer.getUniqueId()))){
            return;
        }

        if(stopInteractWith(block.getType())){
            event.setCancelled(true);
            ChatUtils.info(placer, "You cannot use in this chunk this as it is claimed by another faction.");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();

        Faction claimOwner = FactionHandler.getInstance().getClaimAt(new FChunk(block.getChunk()));

        if(claimOwner == null) return;

        if(claimOwner.equals(FactionHandler.getInstance().getPlayersFaction(player.getUniqueId()))){
            return;
        }
        event.setCancelled(true);
        ChatUtils.info(player, "You cannot use in this chunk this as it is claimed by another faction.");
    }


    private boolean stopInteractWith(Material material){
        if(material.equals(Material.WOODEN_DOOR) || material.equals(Material.TRAP_DOOR)){
            return true;
        }

        return false;
    }

}
