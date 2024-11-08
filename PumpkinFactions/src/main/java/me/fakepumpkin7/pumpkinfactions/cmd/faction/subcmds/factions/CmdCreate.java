package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.factions;

import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds.SubCmd;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.entity.Player;

public class CmdCreate implements SubCmd {
    @Override
    public boolean run(Player player, String[] args) {
        if(args == null || args.length != 1){
            ChatUtils.info(player,"/f create <name>");
        } else {
            String name = args[0];
            runCreateCommand(player, name);
        }

        return true;
    }

    private void runCreateCommand(Player player, String name){
        if(FactionHandler.getInstance().getPlayersFaction(player.getUniqueId()) != null){
            ChatUtils.info(player,"Leave your current faction first");
        } else if (FactionHandler.getInstance().getFactionFromName(name) != null){
            ChatUtils.info(player,"A faction with this name already exists");
        } else {
            FactionHandler.getInstance().createNewFaction(player,name);
            ChatUtils.success(player,"Created "+ name);
        }
    }
}
