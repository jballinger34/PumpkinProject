package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdJoin implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f join <player/faction>");
        } else {
            String name = args[0];
            runJoinCommand(player,name);
        }
        return true;
    }
    private void runJoinCommand(Player player, String name){
        //check for factions with this name, or factions with player with this name
        if(FactionHandler.getInstance().getPlayersFaction(name) == null && FactionHandler.getInstance().getFactionFromName(name) == null){
            //nothing found at all
            ChatUtils.info(player, "No faction/player found with this name.");
            return;
        }
        Faction faction = FactionHandler.getInstance().getFactionFromName(name);
        if(faction == null){
            faction = FactionHandler.getInstance().getPlayersFaction(name);
        }
        tryJoin(faction, player);
    }
    public void tryJoin(Faction faction, Player player){
        if(faction.getName().equalsIgnoreCase("warzone")){
            if(player.isOp()){
                faction.addMember(player);
            } else {
                ChatUtils.warn(player, "You cannot join this faction.");
            }
        }



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
