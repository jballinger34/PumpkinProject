package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdAlly implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {

        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f ally <player/fac name>");
        } else {
            String name = args[0];
            runAllyCommand(player,name);
        }
        return true;
    }
    private void runAllyCommand(Player player, String name){
        Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        Faction toAlly = FactionHandler.getInstance().getFactionFromName(name);
        if(toAlly == null){
            toAlly = FactionHandler.getInstance().getPlayersFaction(name);
        }
        if(toAlly == null){
            ChatUtils.info(player,"Cannot find faction or player with name " + name + ".");
            return;
        }
        if(toAlly.equals(faction)){
            ChatUtils.info(player,"You cannot ally your own faction.");
            return;
        }
        faction.inviteAlly(toAlly);
    }
}
