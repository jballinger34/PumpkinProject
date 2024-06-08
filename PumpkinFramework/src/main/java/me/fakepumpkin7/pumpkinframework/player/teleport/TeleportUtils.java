package me.fakepumpkin7.pumpkinframework.player.teleport;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.combattag.CombatTagUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtils {

    @Getter
    private static long defaultTimedTPseconds = 5;

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
    }

}
