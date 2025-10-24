package me.fakepumpkin7.pumpkinwarzone.crates.listener;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.factions.FactionManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChestToolListenerCmd implements Listener, CommandExecutor {

    private List<UUID> inChestToolMode = new ArrayList<>();

    @EventHandler
    public void onChestInteracted(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.isOp() && player.isSneaking() && event.hasBlock()
                && event.getClickedBlock().getType().equals(Material.CHEST)
                && inChestToolMode.contains(player.getUniqueId())
                && FactionManager.getFactionAPI().isWarzone(event.getClickedBlock().getLocation())){
            //shift click a chest
            if(ChestListener.chestAccessHistory.containsKey(event.getClickedBlock().getLocation())){
                ChestListener.chestAccessHistory.remove(event.getClickedBlock().getLocation());
                ChatUtils.info(player, "Chest removed from loot chests list");
            } else {
                ChestListener.chestAccessHistory.put(event.getClickedBlock().getLocation(), System.currentTimeMillis());
                ChatUtils.info(player, "Chest added to loot chests list");
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        if(!player.isOp()) return true;

        if(inChestToolMode.contains(player.getUniqueId())) {
            inChestToolMode.remove(player.getUniqueId());
            ChatUtils.info(player, "Chest Tool OFF");
        } else {
            inChestToolMode.add(player.getUniqueId());
            ChatUtils.info(player, "Chest Tool ON");
        }
        return true;
    }
}
