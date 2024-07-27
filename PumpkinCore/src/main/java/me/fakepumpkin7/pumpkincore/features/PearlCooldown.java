package me.fakepumpkin7.pumpkincore.features;

import me.fakepumpkin7.pumpkincore.PumpkinCore;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PearlCooldown implements Listener {

    private String metadataTag = "enderpearl-cooldown";
    private double cooldownMS = 15*1000;

    @EventHandler
    public void onPearlThrow(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getType() != Material.ENDER_PEARL) return;

        Player player = event.getPlayer();
        if(player.hasMetadata(metadataTag) && player.getMetadata(metadataTag).get(0).asLong() >= System.currentTimeMillis()){
            //cancel throw
            event.setCancelled(true);
            long ms = player.getMetadata(metadataTag).get(0).asLong() - System.currentTimeMillis();
            int seconds = (int) (ms/1000);
            ChatUtils.info(player, "Ender pearl still on cooldown for "+seconds+" seconds!");
            return;
        }
        player.setMetadata(metadataTag, new FixedMetadataValue(PumpkinCore.getInstance(), System.currentTimeMillis() + cooldownMS));
    }



}
