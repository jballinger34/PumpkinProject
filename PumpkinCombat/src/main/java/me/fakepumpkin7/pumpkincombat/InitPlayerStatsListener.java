package me.fakepumpkin7.pumpkincombat;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class InitPlayerStatsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();


        CombatUtils.setEntityBaseDamage(player,1);
        CombatUtils.setEntityDefence(player,0);
        CombatUtils.setEntityMaxHealth(player, 20);
        CombatUtils.setPlayerSpeed(player,0.2);

        //armourchangeevent is called when a player joins!
        //this is nice!
    }
}
