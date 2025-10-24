package me.fakepumpkin7.pumpkinframework.player.teleport;

import com.google.common.collect.Maps;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class TeleportUtils {

    static long defaultTimedTPseconds = 5;

    //stores players who are teleporting, and the time they started teleporting
    private static final Map<Player, TeleportTask> currentlyTeleporting = Maps.newConcurrentMap();

    public static void timedTeleport(Player player, Location location){
        timedTeleport(player, location, defaultTimedTPseconds);
    }
    public static void timedTeleport(Player player, Location location ,long seconds){
        if(CombatTagUtils.inCombat(player)){
            ChatUtils.warn(player, "You cannot teleport in combat");
            return;
        }
        ChatUtils.notify(player, "Attempting teleport. Stay still and out of combat.");

        TeleportTask tt = new TeleportTask(player, location, seconds);
        tt.runTaskTimer(PumpkinFramework.getInstance(), TeleportTask.interval, TeleportTask.interval);
        currentlyTeleporting.put(player, tt);
    }

    public static boolean isTeleporting(Player player){
        return currentlyTeleporting.containsKey(player);
    }
    public static TeleportTask getTask(Player player){
        return currentlyTeleporting.get(player);
    }


    protected static void endTeleport(Player player){
        currentlyTeleporting.remove(player);
    }

    public static long getDefaultTimedTPseconds() {
        return defaultTimedTPseconds;
    }
}
