package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FWarp;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdDeleteWarp implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null|| args.length != 1){
            ChatUtils.info(player,"/f delwarp <warpname>");
        } else {
            String warpName = args[0];
            runDelWarpCommand(player,warpName);
        }
        return true;
    }


    private void runDelWarpCommand(Player player, String warpName){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());

        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        if(!faction.isAtLeast(player, FactionRank.COLEADER)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }
        FWarp fWarp = faction.getWarpByName(warpName);
        if(fWarp == null){
            if(warpName.equals("home")){
                ChatUtils.info(player,"Your faction does not have a faction home set. /f sethome to set one.");
                return;
            }
            ChatUtils.info(player,"Your faction does not have a warp with this name, /f warps for info.");
            return;
        }
        faction.removeWarp(fWarp);
        if(warpName.equals("home")){
            ChatUtils.notify(player,"Successfully removed faction home.");
            return;
        }
        ChatUtils.notify(player,"Successfully removed warp "+ warpName);

    }

}
