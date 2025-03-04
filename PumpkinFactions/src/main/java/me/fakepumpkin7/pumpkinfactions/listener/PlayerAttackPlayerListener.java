package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAttackPlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player attacker = (Player) event.getDamager();
            Player target = (Player) event.getEntity();
            if(FactionHandler.getInstance().isSameFac(attacker.getUniqueId(), target.getUniqueId())){
                ChatUtils.info(attacker,"You cannot attack your faction members!");
                event.setCancelled(true);
                return;
            }
            if(FactionHandler.getInstance().isAlly(attacker.getUniqueId(), target.getUniqueId())){
                ChatUtils.info(attacker,"You cannot attack your allies!");
                event.setCancelled(true);
                return;
            }
        }
    }

}
