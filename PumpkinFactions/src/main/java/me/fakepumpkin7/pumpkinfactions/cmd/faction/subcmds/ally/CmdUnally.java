package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.ally;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CmdUnally implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        runUnAllyCommand(player);
        return true;
    }
    private void runUnAllyCommand(Player player){
        Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        if(faction.getAlly() == null){
            ChatUtils.info(player,"You do not currently have an ally.");
            return;
        }
        Faction ally = faction.getAlly();
        for(OfflinePlayer allyPlayer : ally.getOnlineMembers()){
            ChatUtils.notify(allyPlayer.getPlayer(),"Your faction is no longer allies with "+ faction.getName() + ".");
        }
        for(OfflinePlayer facPlayer : faction.getOnlineMembers()){
            ChatUtils.notify(facPlayer.getPlayer(),"Your faction is no longer allies with "+ ally.getName() + ".");
        }
        faction.resetAlly();
    }
}
