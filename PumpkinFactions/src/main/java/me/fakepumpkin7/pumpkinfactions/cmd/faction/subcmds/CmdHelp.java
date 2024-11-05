package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdHelp implements SubCmd{


    @Override
    public boolean run(Player player, String[] args) {
        if(args == null){
            printHelp(player, 1);
            return true;
        }
        int page = 1;
        try {
            page = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            ChatUtils.info(player, "Could not parse page number");
        }
        printHelp(player, page);
        return true;

    }

    private void printHelp(Player player, int page){
        int totalPages = 3;
        switch (page){
            case 1:
            default:
                ChatUtils.sendDivider(player, ""+ ChatColor.GREEN + ChatColor.BOLD);
                player.sendMessage("Factions Information and Commands (1/"+totalPages+")");

                player.sendMessage("/f create <faction name> - Create a faction.");
                player.sendMessage("/f invite <player name> - Invite this player to your faction.");
                player.sendMessage("/f join <faction name> - Join a faction.");
                player.sendMessage("/f kick <player name> - Kick a player from your faction.");
                player.sendMessage("/f leave - Leave current faction ");
                player.sendMessage("/f toggleopen - Toggle the faction being invite only and joinable.");

                player.sendMessage("The max faction size is " + Faction.getMaxMembers());

                player.sendMessage("/f who <player/faction name> - View information about a faction.");

                player.sendMessage("/f help <page number> - View help contents");
                ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
                break;
            case 2:
                ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
                player.sendMessage("Factions Information and Commands (2/"+totalPages+")");

                player.sendMessage("/f claim - Claim a chunk for your faction.");
                player.sendMessage("/f unclaim - Remove a claim from a chunk.");
                player.sendMessage("/f map - View claim information around current location.");


                player.sendMessage("/f sethome - Set a faction home for all members to tp to.");
                player.sendMessage("/f home - Teleport to faction home.");

                player.sendMessage("/f setwarp <warp name> - Set a faction warp.");
                player.sendMessage("/f warp <warp name> - Teleport to the specified faction warp.");

                player.sendMessage("A faction gets " + Faction.getPowerPerMember() +" power per member, up to a max of " + Faction.getMaxPower());
                player.sendMessage("If a faction has more chunks claimed than power, their chunks can be over-claimed.");
                player.sendMessage("A faction can set 1 f home and up to TODO other warps");

                ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
                break;
            case 3:
                ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
                player.sendMessage("Factions Information and Commands (3/"+totalPages+")");

                player.sendMessage("/f promote <player name> - Promote player to the next rank within a faction");
                player.sendMessage("/f demote <player name> - Demote player to the previous rank within a faction ");
                player.sendMessage("The faction ranks are: Member, Moderator(*), Co-Leader(**), Leader(***)");
                player.sendMessage("A faction can only have one leader");


                ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
                break;
        }
    }


}
