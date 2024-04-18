package me.fakepumpkin7.pumpkinmmo.cmd;

import me.fakepumpkin7.pumpkinmmo.PumpkinMMO;
import me.fakepumpkin7.pumpkinmmo.SkillHandler;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillCommand implements CommandExecutor {

    SkillHandler skillHandler;

    public SkillCommand(PumpkinMMO plugin){
        skillHandler = plugin.getSkillHandler();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandName, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            for(Skill skill : skillHandler.getSkillList()){
                player.sendMessage(skill.getName());
                player.sendMessage("" + skill.getExpMap().get(player.getUniqueId()));
            }

            return true;
        }





        return false;
    }
}
