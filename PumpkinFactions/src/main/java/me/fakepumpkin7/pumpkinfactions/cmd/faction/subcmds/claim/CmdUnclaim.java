package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.claim;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdUnclaim implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        runUnClaim(player);
        return true;
    }

    private void runUnClaim(Player player){
        Faction faction = FactionHandler.getPlayersFaction(player.getUniqueId());

        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }
        FChunk chunk = new FChunk(player.getLocation().getChunk());

        if(faction.getName().equalsIgnoreCase("warzone")){
            FactionHandler.factionUnClaimLand(faction, chunk);
        }

        if(!faction.isAtLeast(player, FactionRank.MODERATOR)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }

        if(FactionHandler.getClaimAt(chunk) == null || !FactionHandler.getClaimAt(chunk).equals(faction)){
            ChatUtils.info(player,"You do not own this land to unclaim it.");
            return;
        }
        FactionHandler.factionUnClaimLand(faction,chunk);
        ChatUtils.notify(player,"Successfully unclaimed land.");
    }


}
