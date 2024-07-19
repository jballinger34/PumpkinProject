package me.fakepumpkin7.pumpkinframework.player.combattag;


import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.combat.CustomDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;



public class CombatTagListener implements Listener {

    @EventHandler
    public void onCDE(CustomDamageEvent event){
        if(event.getDamage() <= 0) return;

        if(event.getAttacker() instanceof Player){
            CombatTagUtils.flagCombat((Player) event.getAttacker());
        }
        if(event.getTarget() instanceof Player){
            CombatTagUtils.flagCombat((Player) event.getTarget());
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
