package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.claim;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinfactions.struct.FactionRank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdClaim implements SubCmd {

    FactionHandler factionHandler = FactionHandler.getInstance();

    @Override
    public boolean run(Player player, String[] args) {
        runClaim(player);
        return true;
    }
    private void runClaim(Player player){
        Faction faction = factionHandler.getPlayersFaction(player.getUniqueId());
        if(faction == null) {
            ChatUtils.info(player,"You are not in a faction");
            return;
        }

        FChunk chunk = new FChunk(player.getLocation().getChunk());

        //for warzone, deals with warzone fac overclaiming other land
        if(faction.getName().equalsIgnoreCase("warzone")){
            if(factionHandler.getClaimAt(chunk) != null) {
                Faction factionWithClaim = factionHandler.getClaimAt(chunk);
                factionHandler.factionUnClaimLand(factionWithClaim,chunk);
            }
            factionHandler.factionClaimLand(faction, chunk);

        }
        //below deals with normal faction claiming/overclaiming logic

        if(!faction.isAtLeast(player, FactionRank.MODERATOR)){
            ChatUtils.info(player,"You do not have permission to use this.");
            return;
        }

        if(factionHandler.getClaimAt(chunk) != null && faction.getName().equals(factionHandler.getClaimAt(chunk).getName())){
            ChatUtils.info(player,"This is already your claim.");
            return;
        }

        if(faction.getClaims().size() >= faction.getPower()){
            ChatUtils.info(player,"Your faction does not have enough power to claim this land.");
            return;
        }


        if(factionHandler.getClaimAt(chunk) != null){
            Faction factionWithClaim = factionHandler.getClaimAt(chunk);

            if(factionWithClaim.getClaims().size() >= factionWithClaim.getPower()){

                //faction over-claimable, but can only claim on border
                if(!factionHandler.isClaimBorder(chunk, factionWithClaim)){
                    ChatUtils.info(player,"You can only over-claim border claims.");
                    return;
                }

                //land over-claimable
                ChatUtils.notify(player,"Over-claimed land from " + factionWithClaim.getName());

                factionHandler.factionUnClaimLand(factionWithClaim, chunk);
                factionHandler.factionClaimLand(faction, chunk);
            } else {

                //faction has enough power
                ChatUtils.info(player,"This faction cannot be over-claimed.");
            }
            return;
        }
        factionHandler.factionClaimLand(faction, chunk);
        ChatUtils.notify(player,"Successfully claimed land.");
    }
}
