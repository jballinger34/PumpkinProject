package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CmdKick implements SubCmd {


    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f kick <player>");
        } else {
            String name = args[0];
            runKickCommand(player,name);
        }
        return true;
    }
    private void runKickCommand(Player player, String name){
        Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.MODERATOR)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        OfflinePlayer toKick = Bukkit.getOfflinePlayer(name);
        if(toKick == null){
            ChatUtils.info(player,"Cannot find player with name " + name + ".");
            return;
        }
        FactionRank kickerRank = faction.getMembersAndRank().get(player.getUniqueId());
        FactionRank kickeeRank = faction.getMembersAndRank().get(toKick.getUniqueId());
        if(kickeeRank == null || kickeeRank.ordinal() >= kickerRank.ordinal()){
            ChatUtils.info(player,"You cannot kick this player");
            return;
        }
        ChatUtils.info(player,"Kicked " + toKick.getName());
        faction.forceKickMember(toKick.getUniqueId());
        if(toKick.isOnline()){
            ChatUtils.info(Bukkit.getPlayer(toKick.getUniqueId()),"You have been kicked from " + faction.getName());
        }
    }
}
