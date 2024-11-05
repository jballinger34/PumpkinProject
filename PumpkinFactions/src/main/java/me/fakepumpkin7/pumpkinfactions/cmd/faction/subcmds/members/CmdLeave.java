package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdLeave implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        runLeaveCommand(player);
        return true;
    }

    private void runLeaveCommand(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) == null){
            ChatUtils.info(player,"You are not in a faction");
        } else {
            FactionHandler.getPlayersFaction(player.getUniqueId()).removeMember(player);
        }
    }

}
