//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class StatCommand implements ICommand {
    public StatCommand() {
    }

    public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            try {
                Material mat = Material.getMaterial(args[0].toUpperCase());
                if (mat == null) {
                    mat = Material.getMaterial(Integer.parseInt(args[0]));
                }

                if (mat == null) {
                    return false;
                } else {
                    ItemStack item = new ItemStack(mat);
                    if (EnchantmentAPI.getAllValidEnchants(item).size() == 0) {
                        sender.sendMessage(ChatColor.DARK_RED + "That item has no natural enchantments");
                        return true;
                    } else {
                        int level = Integer.parseInt(args[1]);
                        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new StatTask(sender, item, level));
                        return true;
                    }
                }
            } catch (Exception var9) {
                return false;
            }
        } else {
            return false;
        }
    }
}
