package me.fakepumpkin7.pumpkinframework.player.combattag;

import lombok.Getter;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CombatTagUtils {

    protected static String combatTagMetadataTag = "pumpkin-combat-tag";

    protected static long defaultCombatTagSeconds = 15;
    protected static void flagCombat(Player player){
        flagCombat(player, defaultCombatTagSeconds);
    }

    protected static void flagCombat(Player player, long seconds){
        long millis = 1000*seconds;

        if(!inCombat(player)) {
           //add them to the list of players to monitor
            CombatMonitorTask.monitorPlayer(player);
            ChatUtils.warn(player, "You have been combat tagged.");
        }

        player.setMetadata(combatTagMetadataTag, new FixedMetadataValue(PumpkinFramework.getInstance(), System.currentTimeMillis() + millis));

    }



    protected static void unflagCombat(Player player){
        CombatMonitorTask.stopMonitorPlayer(player);
        player.removeMetadata(combatTagMetadataTag, PumpkinFramework.getInstance());
        ChatUtils.notify(player, "You are now out of combat.");
    }

    public static boolean inCombat(Player player) {
        return player.hasMetadata(combatTagMetadataTag);
    }

}
