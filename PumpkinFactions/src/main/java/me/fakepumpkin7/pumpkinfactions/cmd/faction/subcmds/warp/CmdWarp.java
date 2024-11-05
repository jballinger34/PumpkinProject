package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FWarp;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdWarp implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f warp <warpname>");
        } else {
            String warpName = args[0];
            runWarpCommand(player, warpName);
        }
        return true;
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
}
