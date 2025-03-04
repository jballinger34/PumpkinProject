package me.fakepumpkin7.pumpkinframework.player.combattag;


import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;



public class CombatTagListener implements Listener {

    @EventHandler
    public void onCDE(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player) return;
        if(event.getDamage() <= 0) return;

        if(event.getDamager() instanceof Player){
            CombatTagUtils.flagCombat((Player) event.getDamager());
        }
        if(event.getEntity() instanceof Player){
            CombatTagUtils.flagCombat((Player) event.getEntity());
        }
    }

    @EventHandler
    public void onCombatLog(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(CombatTagUtils.inCombat(player)){
            player.setHealth(0.0D);

            for (Entity ent : player.getNearbyEntities(64, 64, 64)) {
                if (ent instanceof Player) {
                    Player p = (Player) ent;
                    ChatUtils.warn(p, player.getName() + " was killed due to logging out in combat,");
                }
            }
        }
    }
}
