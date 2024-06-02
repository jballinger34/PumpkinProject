//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveEnchantCommand implements ICommand {
    public RemoveEnchantCommand() {
    }

    public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            String name = args[0];
            int difference = 0;

            for(int i = 1; i < args.length - difference; ++i) {
                name = name + " " + args[i];
            }

            name = ENameParser.getBukkitName(name);
            Player player = (Player)sender;
            CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(name);
            if (enchantment == null) {
                sender.sendMessage(ChatColor.DARK_RED + name + " is not a registered enchantment!");
            } else {
                player.setItemInHand(enchantment.removeFromItem(player.getItemInHand()));
                player.sendMessage(ChatColor.GREEN + "Enchantment has been has been removed.");
            }
        }

        return true;
    }
}
