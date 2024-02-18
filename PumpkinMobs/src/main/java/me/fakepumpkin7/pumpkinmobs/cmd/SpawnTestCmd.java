package me.fakepumpkin7.pumpkinmobs.cmd;

import me.fakepumpkin7.pumpkinmobs.mobs.zombie.ZombieGrunt;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnTestCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){

            World world = ((Player) commandSender).getWorld();
            new ZombieGrunt(new Location(world,1,65, 16), 1);
        }


        return true;
    }
}
