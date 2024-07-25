package me.fakepumpkin7.pumpkincrates.cmd;

import me.fakepumpkin7.pumpkincrates.CrateRegistry;
import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdGiveCrate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        if(!player.isOp()) return true;

        if(strings.length < 1) return false;
        String id = strings[0];

        CrateRegistry cr = CrateRegistry.getCrateById(id);
        if(cr == null){
            ChatUtils.warn(player, "No crate with id" + id);
            return true;
        }

        Crate crate = cr.getCrate();
        ItemStack crateItem = crate.generateCrate();

        PlayerUtils.addItems(player, crateItem);
        return true;
    }
}
