package me.fakepumpkin7.pumpkinframework.player.combattag;

import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CombatMonitorTask implements Runnable {

    private static List<Player> flaggedPlayers = new ArrayList<>();

   public static void monitorPlayer(Player player){
       flaggedPlayers.add(player);
   }
    public static void stopMonitorPlayer(Player player){
        flaggedPlayers.remove(player);
    }


    @Override
    public void run(){
       List<Player> toUnflag = new ArrayList<>();


        for(Player player : flaggedPlayers){
            if(!player.hasMetadata(CombatTagUtils.combatTagMetadataTag)){
                toUnflag.add(player);
                continue;
            }
            long expireTime = player.getMetadata(CombatTagUtils.combatTagMetadataTag).get(0).asLong();

            if(expireTime < System.currentTimeMillis()){
                toUnflag.add(player);
            }

        }

        for(Player player : toUnflag){
            CombatTagUtils.unflagCombat(player);
        }
    }
}
