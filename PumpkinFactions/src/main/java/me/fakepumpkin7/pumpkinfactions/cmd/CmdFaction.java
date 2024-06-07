package me.fakepumpkin7.pumpkinfactions.cmd;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.factions.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CmdFaction implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(strings.length == 0) return false;

        String subCommand = strings[0];


        if(subCommand.equalsIgnoreCase("help")){
            runHelpCommand(player);
        }
        if(subCommand.equalsIgnoreCase("create")){
            if(strings.length != 2){
                ChatUtils.info(player,"/f create <name>");
            } else {
                String name = strings[1];
                runCreateCommand(player, name);
            }
        }
        if (subCommand.equalsIgnoreCase("who") || subCommand.equalsIgnoreCase("f")) {
            if(strings.length != 2){
                runWhoCommand(player,player.getName());
            } else {
                String name = strings[1];
                runWhoCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("join")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f join <player/faction>");
            } else {
                String name = strings[1];
                runJoinCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("invite") || subCommand.equalsIgnoreCase("inv")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f invite <player>");
            } else {
                String name = strings[1];
                runInviteCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("kick")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f kick <player>");
            } else {
                String name = strings[1];
                runKickCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("promote")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f promote <player>");
            } else {
                String name = strings[1];
                runPromoteCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("demote")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f demote <player>");
            } else {
                String name = strings[1];
                runDemoteCommand(player,name);
            }
        }
        if (subCommand.equalsIgnoreCase("leave")) {
            runLeaveCommand(player);
        }
        if (subCommand.equalsIgnoreCase("toggleopen")) {
            runToggleOpen(player);
        }
        if (subCommand.equalsIgnoreCase("map")) {
            runMap(player);
        }
        if (subCommand.equalsIgnoreCase("claim")) {
            runClaim(player);
        }
        if (subCommand.equalsIgnoreCase("unclaim")) {
            runUnClaim(player);
        }
        if (subCommand.equalsIgnoreCase("sethome")) {
            runSetWarpCommand(player, "home");
        }
        if (subCommand.equalsIgnoreCase("home")) {
            runWarpCommand(player, "home");
        }
        if (subCommand.equalsIgnoreCase("warp")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f warp <warpname>");
            } else {
                String warpName = strings[1];
                runWarpCommand(player, warpName);
            }
        }
        if (subCommand.equalsIgnoreCase("setwarp")) {
            if(strings.length != 2){
                ChatUtils.info(player,"/f setwarp <warpname>");
            } else {
                String warpName = strings[1];
                runSetWarpCommand(player,warpName);
            }
        }
        return true;
    }


    private void runHelpCommand(Player player){
        player.sendMessage("Faction Help!");
        player.sendMessage("/f create");
    }
    private void runCreateCommand(Player player, String name){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) != null){
            ChatUtils.info(player,"Leave your current faction first");
        } else if (FactionHandler.getFactionFromName(name) != null){
            ChatUtils.info(player,"A faction with this name already exists");
        } else {
            FactionHandler.createNewFaction(player,name);
            ChatUtils.success(player,"Created "+ name);
        }
    }
    private void runWhoCommand(Player player, String name){
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

    private void runJoinCommand(Player player, String name){
        if(FactionHandler.getPlayersFaction(name) == null){
            //no faction has player with this name
            //check for factions with this name
            if(FactionHandler.getFactionFromName(name) == null){
                //nothing found at all
                ChatUtils.info(player,"No faction/player found with this name.");
            } else {
                //faction found from players name
                Faction faction = FactionHandler.getFactionFromName(name);
                tryJoin(faction, player);
            }
        } else {
            Faction faction = FactionHandler.getPlayersFaction(name);
            tryJoin(faction, player);
        }
    }
    private void runInviteCommand(Player player, String name) {
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
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
    private void runKickCommand(Player player, String name){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
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
            ChatUtils.info(Bukkit.getPlayer(toKick.getUniqueId()),"You have been kicked from" + faction.getName());
        }
    }

    private void runLeaveCommand(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) == null){
            ChatUtils.info(player,"You are not in a faction");
        } else {
            FactionHandler.getPlayersFaction(player.getUniqueId()).removeMember(player);
        }
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
    private void runDemoteCommand(Player player, String name){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
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

    private void runToggleOpen(Player player){
        if(FactionHandler.getPlayersFaction(player.getUniqueId()) != null){
            Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
            if(faction.isLeader(player)){
                faction.setInviteOnly(!faction.isInviteOnly());
                String msg = faction.isInviteOnly() ? "invite only" : "open";
                ChatUtils.info(player, faction.getName() + " is now " + msg);
            } else {
                ChatUtils.info(player, "Only a Faction's leader can use this command.");
            }
        } else {
            ChatUtils.info(player, "You have to be in a Faction to use this command.");
        }
    }

    private void runMap(Player player) {
        FChunk currentChunk = new FChunk(player.getLocation().getChunk());
        String world = currentChunk.getWorldName();
        Faction playerFac = FactionHandler.getPlayersFaction(player.getUniqueId());
        int chunkX = currentChunk.getX();
        int chunkY = currentChunk.getY();

        String msg = "";
        HashMap<Character, String> identifierAndFacName = new HashMap<>();
        for(int y = -4; y < 5; y++){
            for(int x = -9; x < 10; x++){
                Faction claimed = FactionHandler.getClaimAt(world,chunkX + x, chunkY + y);
                String chatColour = ChatColor.WHITE.toString();
                char identifier = '-';
                if(x == 0 && y == 0){
                    chatColour = ChatColor.AQUA.toString();
                }
                if(claimed != null) {
                    if (claimed.equals(playerFac)) {
                        chatColour = ChatColor.GREEN.toString();
                    } else if (claimed.equals(playerFac.getAlly())) {
                        chatColour = ChatColor.DARK_PURPLE.toString();
                    }

                    identifier = claimed.getName().charAt(0);
                    while (identifierAndFacName.containsKey(identifier) && !identifierAndFacName.get(identifier).equals(claimed.getName())) {
                        identifier++;
                    }
                    identifierAndFacName.put(identifier, claimed.getName());
                }
                msg = msg + chatColour +identifier;
            }
            msg = msg + "\n";
        }
        player.sendMessage("Faction Claims Map:");
        player.sendMessage(msg);
        String msg2 = "";
        player.sendMessage("Key:");
        for(Character c: identifierAndFacName.keySet()){
            msg2 = msg2 + c + ": " + identifierAndFacName.get(c);
        }
        player.sendMessage(msg2);
    }
    private void runClaim(Player player){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.MODERATOR)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        FChunk chunk = new FChunk(player.getLocation().getChunk());
        if(FactionHandler.getClaimAt(chunk) != null){
            ChatUtils.info(player,"This land is already claimed.");
            return;
        }
        FactionHandler.factionClaimLand(faction, chunk);
        ChatUtils.notify(player,"Successfully claimed land.");
    }
    private void runUnClaim(Player player){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());

        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.MODERATOR)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        FChunk chunk = new FChunk(player.getLocation().getChunk());
        if(FactionHandler.getClaimAt(chunk) == null || !FactionHandler.getClaimAt(chunk).equals(faction)){
            ChatUtils.info(player,"You do not own this land to unclaim it.");
            return;
        }
        FactionHandler.factionUnClaimLand(faction,chunk);
        ChatUtils.notify(player,"Successfully unclaimed land.");
    }

    private void runSetWarpCommand(Player player, String warpName){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());

        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }


        Location location = player.getLocation();
        FChunk chunk = new FChunk(location.getChunk());

        if(warpName.equalsIgnoreCase("home")){
            //setting fac home
            if(faction.getWarpByName(warpName) != null){
                ChatUtils.info(player,"Faction already has a home.");
                return;
            }
            if(FactionHandler.getClaimAt(chunk) == null || !FactionHandler.getClaimAt(chunk).equals(faction)){
                ChatUtils.info(player,"Faction home can only be set in a chunk your faction owns.");
                return;
            }
            faction.setHome(location);
            ChatUtils.notify(player,"Successfully set faction home.");
        } else {
            //setting a fac warp
            if(faction.getWarpByName(warpName) != null){
                ChatUtils.info(player,"A warp with this name already exists.");
                return;
            }
            if(FactionHandler.getClaimAt(chunk) == null || !FactionHandler.getClaimAt(chunk).equals(faction)){
                ChatUtils.info(player,"Faction warps can only be set in chunks your faction owns.");
                return;
            }

            FWarp warp = new FWarp(warpName,location, FactionRank.MODERATOR);

            faction.addWarp(warp);
            ChatUtils.notify(player,"Successfully set warp to " + warpName);
        }



    }
    private void runWarpCommand(Player player, String warpName){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        FWarp warp = faction.getWarpByName(warpName);
        if(warp == null){
            ChatUtils.info(player,"Could not find a warp with this name.");
            return;
        }
        if(!faction.isAtLeast(player, warp.getRankNeeded())){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        warp.warpHere(player);
    }


    private void printWho(Player player, Faction faction){

        player.sendMessage(faction.getName());

        if(faction.isInviteOnly()){
            player.sendMessage("Invite Only");
        } else {
            player.sendMessage("Open");
        }

        if(faction.getAlly() != null){
            player.sendMessage("Allied with: " + faction.getAlly().getName());
        }
        List<Player> onlineMembers = faction.getOnlineMembers();
        player.sendMessage("Online (" + onlineMembers.size() + "/" + faction.getMembersAndRank().size()+"):");
        String msg = "";
        for(Player p : onlineMembers){
            msg = msg.concat(faction.getMembersAndRank().get(p.getUniqueId()).getPrefix() + p.getName() + ", ");
        }
        if(msg.length() > 2){
            msg = msg.substring(0,msg.length() - 2);
        }
        player.sendMessage(msg);
        player.sendMessage("Offline:");
        msg = "";
        for(String name : faction.getOfflineMembersNames()){
            msg = msg.concat(faction.getMembersAndRank().get(Bukkit.getOfflinePlayer(name).getUniqueId()).getPrefix() + name + ", ");
        }
        if(msg.length() > 2){
            msg = msg.substring(0,msg.length() - 2);
        }
        player.sendMessage(msg);

    }
    public void tryJoin(Faction faction, Player player){
        if(faction.isInviteOnly()){
            if(faction.isInvited(player)){
                faction.addMember(player);
                faction.deleteInvite(player);
            } else {
                ChatUtils.info(player, "This faction is invite only.");
            }
        } else {
            faction.addMember(player);
        }
    }
}
