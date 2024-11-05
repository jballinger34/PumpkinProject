package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds;

import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally.CmdAlly;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally.CmdUnally;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.claim.CmdClaim;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.claim.CmdUnclaim;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.factions.CmdCreate;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.factions.CmdDisband;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members.*;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdDeleteWarp;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdSetWarp;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdWarp;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp.CmdWarpInfo;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public enum SubCommandRegistry {

    CMD_HELP(new CmdHelp(), "help"),
    CMD_CREATE(new CmdCreate(), "create"),
    CMD_WHO(new CmdWho(), "who", "f", "info"),
    CMD_JOIN(new CmdJoin(), "join"),
    CMD_INVITE(new CmdInvite(), "inv", "invite"),
    CMD_KICK(new CmdKick(), "kick", "boot"),
    CMD_PROMOTE(new CmdPromote(), "promote"),
    CMD_DEMOTE(new CmdDemote(), "demote"),
    CMD_LEAVE(new CmdLeave(), "leave"),
    CMD_TOGGLE_OPEN(new CmdToggleOpen(), "toggleopen"),
    CMD_MAP(new CmdMap(), "map"),
    CMD_CLAIM(new CmdClaim(), "claim"),
    CMD_UNCLAIM(new CmdUnclaim(), "unclaim"),
    CMD_WARP(new CmdWarp(), "warp" ),
    CMD_WARPS(new CmdWarpInfo(), "warps", "warpinfo"),
    CMD_SET_WARP(new CmdSetWarp(), "setwarp"),
    CMD_DELETE_WARP(new CmdDeleteWarp(), "delwarp", "deletewarp"),
    CMD_ALLY(new CmdAlly(), "ally"),
    CND_UNALLY(new CmdUnally(), "unally"),
    CMD_DISBAND(new CmdDisband(), "disband"),






    ;

    final ArrayList<String> identifiers = new ArrayList<>();
    final SubCmd subCmd;

    SubCommandRegistry(SubCmd subCmd, String name, String... aliases){
        this.subCmd = subCmd;
        this.identifiers.add(name.toLowerCase());
        if(aliases != null) {
            for(String alias : aliases){
                this.identifiers.add(alias.toLowerCase());
            }
        }
    }

    private SubCmd getSubCmd() {
        return this.subCmd;
    }
    private static SubCommandRegistry getByName(String subCmd){
        for(SubCommandRegistry subCommand : SubCommandRegistry.values()){
            if(subCommand.identifiers.contains(subCmd.toLowerCase())){
                return subCommand;
            }
        }
        return CMD_HELP;
    }
    public static boolean run(String subCmd, Player player, String[] args){
        return getByName(subCmd).getSubCmd().run(player, args);
    }
    public boolean run(Player player, String[] args){
        return getSubCmd().run(player,args);
    }
}
