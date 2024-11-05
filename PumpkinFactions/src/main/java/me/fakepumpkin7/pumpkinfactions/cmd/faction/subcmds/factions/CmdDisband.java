package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.factions;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdDisband implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        runDisbandCommand(player);
        return true;
    }



    private void runDisbandCommand(Player player){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.LEADER)){
            ChatUtils.info(player,"You must be the faction's leader to disband it.");
            return;
        }
        FactionHandler.disbandFaction(faction);
        ChatUtils.success(player,"Successfully disbanded faction.");
    }

}
