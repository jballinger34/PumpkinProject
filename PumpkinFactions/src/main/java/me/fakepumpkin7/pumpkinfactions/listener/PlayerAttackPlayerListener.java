package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.event.combat.CustomDamageEvent;
import me.fakepumpkin7.pumpkinframework.factions.FactionHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerAttackPlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCD(CustomDamageEvent event){
        if(event.getAttacker() instanceof Player && event.getTarget() instanceof Player){
            Player attacker = (Player) event.getAttacker();
            Player target = (Player) event.getTarget();
            if(FactionHandler.isInSameFaction(attacker, target)){
                ChatUtils.info(attacker,"You cannot attack your faction members!");
                event.setCancelled(true);
            }
        }
    }

}
