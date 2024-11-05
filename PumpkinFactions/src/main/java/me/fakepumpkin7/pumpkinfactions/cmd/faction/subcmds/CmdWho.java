package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdWho implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            runWhoCommand(player,player.getName());
        } else {
            String name = args[0];
            runWhoCommand(player,name);
        }
        return true;
    }
    private void runWhoCommand(Player player, String name){
        if(name.equalsIgnoreCase("warzone")){
            printWarZoneWho(player);
            return;
        }


        if(FactionHandler.getPlayersFaction(name) == null){
            //no faction has player with this name
            //check for factions with this name
            if(FactionHandler.getFactionFromName(name) == null){
                //nothing found
                ChatUtils.info(player,"No faction/player found with this name.");
            } else {
                printWho(player, FactionHandler.getFactionFromName(name));
            }
        } else {
            printWho(player, FactionHandler.getPlayersFaction(name));
        }
    }
    private void printWarZoneWho(Player player){
        ChatUtils.sendDivider(player, ""+ ChatColor.DARK_RED + ChatColor.BOLD);
        player.sendMessage(""+ChatColor.DARK_RED + ChatColor.BOLD + "WarZone");
        ChatUtils.sendDivider(player, ""+ChatColor.DARK_RED + ChatColor.BOLD);
    }
    private void printWho(Player player, Faction faction) {
        ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
        player.sendMessage(faction.getName());

        if (faction.isInviteOnly()) {
            player.sendMessage("Invite Only");
        } else {
            player.sendMessage("Open");
        }

        if (faction.getAlly() != null) {
            player.sendMessage("Allied with: " + faction.getAlly().getName());
        }

        player.sendMessage("Claims: " + faction.getClaims().size());
        player.sendMessage("Power: " + faction.getPower());

        List<Player> onlineMembers = faction.getOnlineMembers();
        player.sendMessage("Online (" + onlineMembers.size() + "/" + faction.getMembersAndRank().size() + "):");
        String msg = "";
        for (Player p : onlineMembers) {
            msg = msg.concat(faction.getMembersAndRank().get(p.getUniqueId()).getPrefix() + p.getName() + ", ");
        }
        if (msg.length() > 2) {
            msg = msg.substring(0, msg.length() - 2);
        }
        player.sendMessage(msg);
        player.sendMessage("Offline:");
        msg = "";
        for (String name : faction.getOfflineMembersNames()) {
            msg = msg.concat(faction.getMembersAndRank().get(Bukkit.getOfflinePlayer(name).getUniqueId()).getPrefix() + name + ", ");
        }
        if (msg.length() > 2) {
            msg = msg.substring(0, msg.length() - 2);
        }
        player.sendMessage(msg);
        ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
    }
}
