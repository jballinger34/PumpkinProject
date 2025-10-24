package me.fakepumpkin7.pumpkinwarzone.crates.cmd;

import me.fakepumpkin7.pumpkinwarzone.crates.tasks.WashupTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceWashupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()){
            return true;
        }

        WashupTask.forceWashup();

        return true;
    }
}
