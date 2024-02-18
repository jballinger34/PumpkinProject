package me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class InitCustomDefenceListener implements Listener {

    @EventHandler
    public void initCDonPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        CombatUtils.setEntityDefence(player,0);



    }
}
