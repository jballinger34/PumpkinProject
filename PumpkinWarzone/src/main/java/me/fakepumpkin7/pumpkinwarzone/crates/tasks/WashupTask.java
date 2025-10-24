package me.fakepumpkin7.pumpkinwarzone.crates.tasks;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinwarzone.PumpkinWarzone;
import me.fakepumpkin7.pumpkinwarzone.crates.listener.ChestListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class WashupTask extends BukkitRunnable {

    public final long washupCooldown = 30l;
    public long lastEventTime = System.currentTimeMillis();
    public static boolean washupActive = false;
    @Override
    public void run() {
        //give initial message saying washup, then a min later start the wash-up, which
        //sets washupActive true and resets all cds, so if any chests are opened while this is true they get upgraded
        // then 3 mins later set washupActive back to false
        ChatUtils.broadcast("WASH-UP COMING IN 1 MIN");

        Bukkit.getScheduler().runTaskLater(PumpkinWarzone.getInstance(), () -> {
            ChatUtils.broadcast("WASH-UP HERE");
            washupActive = true;
            //set each chest to be able to be opened now
            for(Location loc : ChestListener.chestAccessHistory.keySet()){
                ChestListener.chestAccessHistory.put(loc, System.currentTimeMillis());
            }
            lastEventTime = System.currentTimeMillis();

        },60*20L);
        Bukkit.getScheduler().runTaskLater(PumpkinWarzone.getInstance(), () -> {
            washupActive = false;
        },3*60*20L);
    }

    public static void forceWashup(){
        Bukkit.getScheduler().runTask(PumpkinWarzone.getInstance(), new WashupTask());
    }
}
