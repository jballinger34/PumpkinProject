package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.warp;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FWarp;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdWarpInfo implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        runWarpInfoCommand(player);
        return true;
    }

    private void runWarpInfoCommand(Player player){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction.");
            return;
        }
        if(faction.getWarps().size() == 0){
            ChatUtils.info(player,"Your faction does not have any warps.");
            return;
        }
        String msg = "";
        for(FWarp warp : faction.getWarps()){
            msg = msg + warp.getName() +": x: "
                    + Math.round(warp.getLocation().getX()) + ", y: "
                    + Math.round(warp.getLocation().getY()) + ", z: "
                    + Math.round(warp.getLocation().getZ()) + "\n";
        }
        ChatUtils.sendDivider(player, ChatColor.GREEN.toString() + ChatColor.BOLD);
        player.sendMessage(msg);
        ChatUtils.sendDivider(player, ChatColor.GREEN.toString() + ChatColor.BOLD);
    }
}
