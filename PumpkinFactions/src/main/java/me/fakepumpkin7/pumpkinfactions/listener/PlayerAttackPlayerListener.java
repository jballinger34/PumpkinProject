package me.fakepumpkin7.pumpkinfactions.listener;

import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.OfflinePlayer;
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
