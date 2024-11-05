package me.fakepumpkin7.pumpkinfactions.cmd.faction.subcmds;

import me.fakepumpkin7.pumpkinfactions.Faction;
import me.fakepumpkin7.pumpkinfactions.FactionHandler;
import me.fakepumpkin7.pumpkinfactions.struct.FChunk;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CmdMap implements SubCmd {

    @Override
    public boolean run(Player player, String[] args) {
        runMap(player);
        return true;
    }
    private void runMap(Player player) {
        ChatUtils.sendDivider(player, ""+ ChatColor.GREEN + ChatColor.BOLD);
        FChunk currentChunk = new FChunk(player.getLocation().getChunk());
        String world = currentChunk.getWorldName();
        Faction playerFac = FactionHandler.getPlayersFaction(player.getUniqueId());
        int chunkX = currentChunk.getX();
        int chunkY = currentChunk.getY();

        String msg = "";
        HashMap<Character, String> identifierAndFacName = new HashMap<>();
        for(int y = -4; y < 5; y++){
            for(int x = -9; x < 10; x++){
                Faction claimed = FactionHandler.getClaimAt(world,chunkX + x, chunkY + y);
                String chatColour = ChatColor.WHITE.toString();
                char identifier = '-';
                if(x == 0 && y == 0){
                    chatColour = ChatColor.AQUA.toString();
                }
                if(claimed != null) {
                    if (claimed.equals(playerFac)) {
                        chatColour = ChatColor.GREEN.toString();
                    } else if (claimed.getName().equalsIgnoreCase("warzone")){
                        chatColour = ChatColor.DARK_RED.toString();
                    } else if (claimed.getAlly() != null && claimed.getAlly().equals(playerFac)) {
                        chatColour = ChatColor.LIGHT_PURPLE.toString();
                    }


                    identifier = claimed.getName().charAt(0);
                    while (identifierAndFacName.containsKey(identifier) && !identifierAndFacName.get(identifier).equals(claimed.getName())) {
                        identifier++;
                    }
                    if(claimed.getName().equalsIgnoreCase("warzone")){
                        identifier = '+';
                    }
                    identifierAndFacName.put(identifier, claimed.getName());
                }
                msg = msg + chatColour +identifier;
            }
            msg = msg + "\n";
        }
        player.sendMessage("Faction Claims Map:");
        player.sendMessage(msg);
        String msg2 = "";
        player.sendMessage("Key:");
        for(Character c: identifierAndFacName.keySet()){
            msg2 = msg2 + c + ": " + identifierAndFacName.get(c)+ " ";
        }
        player.sendMessage(msg2);
        ChatUtils.sendDivider(player, ""+ChatColor.GREEN + ChatColor.BOLD);
    }
}
