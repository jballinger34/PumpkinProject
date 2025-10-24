package me.fakepumpkin7.pumpkinwarzone.crates.cmd;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinwarzone.crates.tasks.WashupTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WashupCmd implements CommandExecutor {

    WashupTask washupTask;
    public WashupCmd(WashupTask task){
        this.washupTask = task;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;

        long lastWashupMs = washupTask.lastEventTime;
        long washupCooldownMs = washupTask.washupCooldown * 1000 * 60;

        long nextWashupMs = lastWashupMs + washupCooldownMs;

        long waitMs = nextWashupMs - System.currentTimeMillis();
        long waitMins = (waitMs*60)/1000;

        ChatUtils.info(player, "Next WarZone Wash-up: "+  waitMins + " Mins!" );

        return true;
    }
}
