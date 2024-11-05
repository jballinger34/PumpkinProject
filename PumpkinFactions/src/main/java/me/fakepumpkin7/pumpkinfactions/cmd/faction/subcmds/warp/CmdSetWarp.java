package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.FWarp;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CmdSetWarp implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f setwarp <warpname>");
        } else {
            String warpName = args[0];
            runSetWarpCommand(player,warpName);
        }
        return true;
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


}
