package me.fakepumpkin7.pumpkinarmour.cmd;

import me.fakepumpkin7.pumpkinarmour.struct.ArmourSetRegistry;
import me.fakepumpkin7.pumpkinarmour.struct.ArmourSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GiveArmourSetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!commandSender.isOp()){
            System.out.println("NOT OP");
            return false;
        }
        if(strings.length != 1){
            return false;
        }
        if(!(commandSender instanceof Player)){
            System.out.println("ONLY PLAYER CAN USE THIS");
            return false;
        }
        Player player = (Player) commandSender;
        String id = strings[0];
        ArmourSet set = ArmourSetRegistry.getSetById(id);
        if(set == null){
            System.out.println("invalid set id");
            //could print out all set ids
            return false;
        }
        ArmourSetRegistry.getSetById(id).giveSetToPlayer(player);
        return true;


    }
}
