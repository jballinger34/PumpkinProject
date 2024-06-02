//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.enchanting.VanillaEnchantment;
import com.rit.sucy.service.ICommand;
import com.rit.sucy.service.PermissionNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EnchantListCommand implements ICommand {
    public EnchantListCommand() {
    }

    public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PermissionNode.LIST.getNode())) {
            return false;
        } else {
            if (args.length > 0) {
                try {
                    int page = Integer.parseInt(args[0]);
                    if (EnchantmentAPI.getEnchantments().size() <= 0) {
                        sender.sendMessage(ChatColor.DARK_RED + "There are no registered enchantments to display");
                    } else {
                        ArrayList<CustomEnchantment> enchants = new ArrayList(EnchantmentAPI.getEnchantments());
                        Collections.sort(enchants);
                        Iterator iterator = enchants.iterator();

                        while(iterator.hasNext()) {
                            CustomEnchantment next = (CustomEnchantment)iterator.next();
                            if (next instanceof VanillaEnchantment) {
                                iterator.remove();
                            }
                        }

                        if (page * 9 > enchants.size() + 8) {
                            page = (enchants.size() + 8) / 9;
                        } else if (page < 1) {
                            page = 1;
                        }

                        sender.sendMessage(ChatColor.DARK_GREEN + "Enchantment Details - Page " + page + " / " + (enchants.size() + 8) / 9);

                        for(int i = (page - 1) * 9; i < page * 9 && i < enchants.size(); ++i) {
                            sender.sendMessage(ChatColor.GOLD + ((CustomEnchantment)enchants.get(i)).name() + ChatColor.GRAY + " - " + (((CustomEnchantment)enchants.get(i)).getDescription() == null ? "No description" : ((CustomEnchantment)enchants.get(i)).getDescription()));
                        }
                    }

                    return true;
                } catch (NumberFormatException var11) {
                }
            }

            String message = " Registered enchantments: ";
            int count = 0;
            if (EnchantmentAPI.getEnchantments().size() > 0) {
                ArrayList<CustomEnchantment> enchants = new ArrayList(EnchantmentAPI.getEnchantments());
                Collections.sort(enchants);
                Iterator var9 = enchants.iterator();

                while(var9.hasNext()) {
                    CustomEnchantment enchantment = (CustomEnchantment)var9.next();
                    if (!(enchantment instanceof VanillaEnchantment)) {
                        message = message + ChatColor.GOLD + enchantment.name() + ChatColor.GRAY + ", ";
                        ++count;
                    }
                }

                message = message.substring(0, message.length() - 2);
            }

            sender.sendMessage(ChatColor.GREEN + "" + count + message);
            return true;
        }
    }
}
