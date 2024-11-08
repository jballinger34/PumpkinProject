package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CmdInvite implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f invite <player>");
        } else {
            String name = args[0];
            runInviteCommand(player,name);
        }
        return true;
    }
    private void runInviteCommand(Player player, String name) {
        Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(faction.isAtLeast(player, FactionRank.MODERATOR)){

            Player toInvite = Bukkit.getPlayer(name);
            if(toInvite == null){
                ChatUtils.info(player,"Player not found");
            } else {
                ChatUtils.notify(player,"Invited " + name);
                faction.invitePlayer(toInvite);
            }
        } else {
            ChatUtils.info(player,"You must be at least a moderator in your faction to use this.");
        }
    }
}
