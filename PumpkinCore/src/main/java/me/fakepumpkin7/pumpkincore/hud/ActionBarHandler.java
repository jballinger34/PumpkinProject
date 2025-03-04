package me.fakepumpkin7.pumpkincore.hud;

import me.fakepumpkin7.pumpkinframework.hud.ActionBar;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatMonitorTask;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagUtils;
import me.fakepumpkin7.pumpkinframework.player.teleport.TeleportTask;
import me.fakepumpkin7.pumpkinframework.player.teleport.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarHandler implements Runnable{

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){

            if(CombatTagUtils.inCombat(player)){

                long taskStart = CombatTagUtils.getWhenCombatStarted(player);
                long expireTime = CombatTagUtils.getWhenCombatEnds(player);
                long totalTime = expireTime - taskStart;

                long currentTime = System.currentTimeMillis() - taskStart;

                ActionBar.sendPercentageActionBar(player, "Combat Tagged: ", currentTime, totalTime, ChatColor.RED, ChatColor.WHITE);
                continue;
            }
            if(TeleportUtils.isTeleporting(player)){
                TeleportTask tt = TeleportUtils.getTask(player);

                long taskStart = tt.getStartTime();

                long totalTime = tt.getSeconds()*1000;
                long currentTime = System.currentTimeMillis() - taskStart;


                ActionBar.sendPercentageActionBar(player,"Teleporting: ", currentTime, totalTime);

                continue;
            }
        }
    }
}
