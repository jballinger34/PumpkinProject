package me.fakepumpkin7.pumpkinframework.items.interactive;

import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InteractiveItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) return true;
        if(strings.length == 0 ){
            StringBuilder sb = new StringBuilder();

            for(String str : InteractiveItemUtils.getRegisteredItems().keySet()){
                sb.append(str).append(",");
            }
            sb.deleteCharAt(sb.length() -1);
            commandSender.sendMessage(sb.toString());
        }
        if(strings.length == 1){
            if(!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;

            String id = strings[0];
            ItemStack item = InteractiveItemUtils.getItem(id);

            PlayerUtils.addItems(player, item);
        }
        return true;
    }
}
