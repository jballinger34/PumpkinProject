package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;


public class PlayerEnterClaimListener implements Listener {
    static HashMap<UUID, FChunk> uuidToLastChunk = new HashMap<>();



    @EventHandler
    public void onPlayerEnterNewClaim(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        FChunk currentChunk = new FChunk(player.getLocation().getChunk());
        FChunk lastChunk = uuidToLastChunk.get(uuid);
        uuidToLastChunk.put(uuid, currentChunk);

        if(lastChunk == null){
            return;
        }
        if(lastChunk.equals(currentChunk)){
            //same chunk
            return;
        }
        Faction oldFac = FactionHandler.getClaimAt(lastChunk);
        Faction newFac = FactionHandler.getClaimAt(currentChunk);
        //wilderness to wilderness
        if(oldFac == null && newFac == null){
            return;
        }
        //claimed land to wilderness
        if(oldFac != null && newFac == null){
            //entered wilderness
            ChatUtils.notify(player, "Entered " + ChatColor.DARK_GREEN + "Wilderness");
            return;
        }
        //we know now newFac isnt null

        //claim to claim, same fac
        if(newFac.equals(oldFac)){
            return;
        }
        //claim to claim, new fac
        Faction playerFac = FactionHandler.getPlayersFaction(uuid);

        ChatColor color = ChatColor.WHITE;
        if(newFac.equals(playerFac)){
            color = ChatColor.GREEN;
        } else if(newFac.getName().equalsIgnoreCase("warzone")){
            color = ChatColor.DARK_RED;
        } else if (newFac.getAlly() != null && newFac.getAlly().equals(playerFac)){
            color = ChatColor.DARK_PURPLE;
        }
        ChatUtils.notify(player, "Entered " + color + newFac.getName());

    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        uuidToLastChunk.remove(event.getPlayer().getUniqueId());
    }


}
