package me.fakepumpkin7.pumpkinfactions.cmd.faction;

import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.*;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally.CmdAlly;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally.CmdUnally;

import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.factions.CmdDisband;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdDeleteWarp;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdSetWarp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import static me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCommandRegistry.*;

public class CmdFaction implements CommandExecutor {

    //two ways to run subcommands:
    //examples both run help command for player with no arguments
    //
    //return SubCommandRegistry.run("help", player, null);
    //and
    //return CMD_HELP.run(player, null);
    //
    //chosen to use 2nd option as doesnt change if aliases for
    //players running the command change
    //
    //i.e if we change /f help to /f helppage (by changing) the alias
    //in the enum, wed have to change it here as well


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        //run help if no args given (/f)
        if(strings.length == 0) {
            //runs help command with no args, should print help page 1
            return CMD_HELP.run(player, null);
        }
        //run help, look for page
        String subCommand = strings[0];

        //subargs may be null, if this is the case, all we have to deal with it the subcommand
        String[] subArgs = null;
        if(strings.length >= 2) {
            subArgs = Arrays.copyOfRange(strings, 1, strings.length);
        }

        //cases where a command runs another with a specific argument,
        //i.e "/f sethome" actually runs "/f setwarp home"
        if (subCommand.equalsIgnoreCase("sethome")) {
            return CMD_SET_WARP.run(player, new String[]{"home"});
        }
        if (subCommand.equalsIgnoreCase("home")) {
            return CMD_WARP.run(player, new String[]{"home"});
        }
        if (subCommand.equalsIgnoreCase("delhome")) {
            return CMD_DELETE_WARP.run(player, new String[]{"home"});
        }


        SubCommandRegistry.run(subCommand, player, subArgs);


        //f map <on/off>
        //f claim radius <size>
        //f claim auto <on/off>


        return true;
    }
}
