package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CmdPromote implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f promote <player>");
        } else {
            String name = args[0];
            runPromoteCommand(player,name);
        }
        return true;
    }
    private void runPromoteCommand(Player player, String name){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        OfflinePlayer toPromote = Bukkit.getOfflinePlayer(name);
        if(toPromote == null){
            ChatUtils.info(player,"Cannot find player with name " + name + ".");
            return;
        }
        FactionRank promoterRank = faction.getMembersAndRank().get(player.getUniqueId());
        FactionRank promoteeRank = faction.getMembersAndRank().get(toPromote.getUniqueId());
        if(promoteeRank == null || promoteeRank.ordinal() >= promoterRank.ordinal()){
            ChatUtils.info(player,"You cannot promote this player.");
            return;
        }
        if(promoterRank == FactionRank.LEADER && promoteeRank == FactionRank.COLEADER){
            faction.swapLeaders(toPromote.getUniqueId());
        } else {
            faction.promoteMember(toPromote.getUniqueId());
        }
        String newRank = faction.getMembersAndRank().get(toPromote.getUniqueId()).toString();

        ChatUtils.info(player,"Promoted " + toPromote.getName() + " to a Faction " + newRank);
        if(toPromote.isOnline()){
            ChatUtils.info(Bukkit.getPlayer(toPromote.getUniqueId()),"You have been promoted to a Faction " + newRank);
        }
    }
}
