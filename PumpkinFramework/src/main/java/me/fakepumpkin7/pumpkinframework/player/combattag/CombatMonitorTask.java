package me.fakepumpkin7.pumpkinframework.player.combattag;

import com.google.common.collect.Maps;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CombatMonitorTask implements Runnable {


    //map of player to the START time of when they were flagged. This allows
    //us to access the start time elsewhere
    //the END time is stored in metadata on the player
    private static Map<Player, Long> flaggedPlayers = Maps.newConcurrentMap();

    public static void monitorPlayer(Player player){
       flaggedPlayers.put(player, System.currentTimeMillis());
    }
    public static void stopMonitorPlayer(Player player){
        flaggedPlayers.remove(player);
    }


    @Override
    public void run(){
       List<Player> toUnflag = new ArrayList<>();


        for(Player player : flaggedPlayers.keySet()){
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
    protected static long getStartTime(Player player){
        return flaggedPlayers.get(player);
    }
}
