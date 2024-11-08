package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CmdDemote implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f demote <player>");
        } else {
            String name = args[0];
            runDemoteCommand(player,name);
        }
        return true;
    }
    private void runDemoteCommand(Player player, String name){
        Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        OfflinePlayer toDemote = Bukkit.getOfflinePlayer(name);
        if(toDemote == null){
            ChatUtils.info(player,"Cannot find player with name " + name + ".");
            return;
        }
        FactionRank demoterRank = faction.getMembersAndRank().get(player.getUniqueId());
        FactionRank demoteeRank = faction.getMembersAndRank().get(toDemote.getUniqueId());
        if(demoteeRank == null || demoteeRank.ordinal() >= demoterRank.ordinal()){
            ChatUtils.info(player,"You cannot demote this player.");
            return;
        }
        if(demoteeRank.equals(FactionRank.MEMBER)){
            ChatUtils.info(player,"This player cannot be demoted any more.");
            return;
        }
        ChatUtils.info(player,"Demoted " + toDemote.getName());
        faction.demoteMember(toDemote.getUniqueId());
        if(toDemote.isOnline()){
            ChatUtils.info(Bukkit.getPlayer(toDemote.getUniqueId()),"You have been demoted to a Faction " + faction.getMembersAndRank().get(toDemote.getUniqueId()).toString());
        }
    }
}
