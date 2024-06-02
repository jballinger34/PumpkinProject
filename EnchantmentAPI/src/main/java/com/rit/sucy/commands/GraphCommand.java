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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class GraphCommand implements ICommand {
    private long timer = 0L;

    public GraphCommand() {
    }

    public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            try {
                Material mat = Material.getMaterial(args[0].toUpperCase());
                if (mat == null) {
                    mat = Material.getMaterial(Integer.parseInt(args[0]));
                }

                if (mat == null) {
                    return false;
                } else {
                    ItemStack item = new ItemStack(mat);
                    String name = args[1];

                    for(int i = 2; i < args.length; ++i) {
                        name = name + " " + args[i];
                    }

                    CustomEnchantment enchant = EnchantmentAPI.getEnchantment(ENameParser.getBukkitName(name));
                    if (!enchant.canEnchantOnto(item)) {
                        sender.sendMessage(ChatColor.DARK_RED + "That enchantment doesn't work on that item");
                        return true;
                    } else if (System.currentTimeMillis() - this.timer < 10000L) {
                        sender.sendMessage(ChatColor.DARK_RED + "Please give the server a quick break!");
                        sender.sendMessage(ChatColor.DARK_RED + "The command should only be used every 10 seconds!");
                        return true;
                    } else {
                        this.timer = System.currentTimeMillis();
                        sender.sendMessage(ChatColor.DARK_GREEN + "Calculating probabilities...");
                        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new GraphTask(sender, item, enchant));
                        return true;
                    }
                }
            } catch (Exception var10) {
                return false;
            }
        } else {
            return false;
        }
    }
}
