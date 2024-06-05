package me.fakepumpkin7.pumpkinfactions.cmd;

import me.fakepumpkin7.pumpkinfactions.struct.Faction;
import me.fakepumpkin7.pumpkinfactions.struct.FactionHandler;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdFaction implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(strings.length == 0) return false;

        String subCommand = strings[0];


        if(subCommand.equals("help")){
            return runHelpCommand(player);
        }
        if(subCommand.equals("create")){
            if(strings.length != 2){
                ChatUtils.info(player,"/f create <name>");
                return true;
            } else {
                String name = strings[1];
                return runCreateCommand(player, name);
            }
        }
        if (subCommand.equals("who") || subCommand.equals("f")) {
            if(strings.length != 2){
                return runWhoCommand(player,player.getName());
            } else {
                String name = strings[1];
                return runWhoCommand(player,name);
            }
        }
        if (subCommand.equals("join")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f who <player/faction>");
                return true;
            } else {
                String name = strings[1];
                return runJoinCommand(player,name);
            }
        }
        if (subCommand.equals("leave")) {
            return runLeaveCommand(player);
        }




        return true;
    }

    private boolean runHelpCommand(Player player){
        player.sendMessage("Faction Help!");
        player.sendMessage("/f create");
        return true;
    }
    private boolean runCreateCommand(Player player, String name){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) != null){
            ChatUtils.error(player,"Leave your current faction first");
            return true;
        } else if (FactionHandler.getFactionFromName(name) != null){
            ChatUtils.error(player,"A faction with this name already exists");
            return true;
        } else {
            FactionHandler.createNewFaction(player,name);
            return true;
        }
    }
    private boolean runWhoCommand(Player player, String name){
        if(FactionHandler.getPlayersFaction(name) == null){
            //no faction has player with this name
            //check for factions with this name
            if(FactionHandler.getFactionFromName(name) == null){
                //nothing found
                ChatUtils.info(player,"No faction/player found with this name.");
                return true;
            } else {
                printWho(player, FactionHandler.getFactionFromName(name));
                return true;
            }
        } else {
            printWho(player, FactionHandler.getPlayersFaction(name));
            return true;
        }
    }

    private boolean runJoinCommand(Player player, String name){
        if(FactionHandler.getPlayersFaction(name) == null){
            //no faction has player with this name
            //check for factions with this name
            if(FactionHandler.getFactionFromName(name) == null){
                //nothing found at all
                ChatUtils.info(player,"No faction/player found with this name.");
                return true;
            } else {
                FactionHandler.getFactionFromName(name).addMember(player);
                return true;
            }
        } else {
            FactionHandler.getPlayersFaction(name).addMember(player);
            return true;
        }
    }
    private boolean runLeaveCommand(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) == null){
            ChatUtils.error(player,"You are not in a faction");
            return true;
        } else {
            FactionHandler.getPlayersFaction(player.getUniqueId()).removeMember(player);
            return true;
        }
    }

    private void printWho(Player player, Faction faction){
        player.sendMessage(faction.getName());
        if(faction.getAlly() != null){
            player.sendMessage("Allied with: " + faction.getAlly().getName());
        }
        player.sendMessage("Members:");
        String members = "";
        for(UUID uuid : faction.getMembersAndRank().keySet()){
            members = members + Bukkit.getOfflinePlayer(uuid).getName() + ", ";
        }
        player.sendMessage(members);
    }
}
