package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.members;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdToggleOpen implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        runToggleOpen(player);
        return true;
    }
    private void runToggleOpen(Player player){
        if(FactionHandler.getInstance().getPlayersFaction(player.getUniqueId()) != null){
            Faction faction = FactionHandler.getInstance().getPlayersFaction(player.getUniqueId());
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
}
