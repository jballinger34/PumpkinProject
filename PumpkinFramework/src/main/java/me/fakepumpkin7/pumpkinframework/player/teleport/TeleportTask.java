package me.fakepumpkin7.pumpkinframework.player.teleport;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable {

    //runnable repeated every interval ticks
    public static long interval = 5;
    private Player player;
    private Location oldLoc;
    private long seconds;
    private Location location;
    private int count = 0;

    public TeleportTask(Player player, Location location, long seconds){
        this.player = player;
        this.seconds = seconds;
        this.location = location;
    }
    @Override
    public void run() {
        count += interval;


        if(oldLoc == null){
            oldLoc = player.getLocation();
        }

        if(CombatTagUtils.inCombat(player)){
            //players in combat
            canceltp();
            return;
        }
        if(oldLoc.distance(player.getLocation()) >= 0.5){
            //player has moved
            canceltp();
            return;
        }
        oldLoc = player.getLocation();

        if(seconds*20 < count){
            tp();
            return;
        }
    }
    private void tp(){
        player.teleport(location);
        ChatUtils.notify(player, "Teleporting...");
        this.cancel();
    }
    private void canceltp(){
        ChatUtils.warn(player, "Teleport cancelled as you moved or entered combat.");
        this.cancel();
    }


}
