//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.CommandHandler;
import com.rit.sucy.service.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Commander extends CommandHandler {
    public Commander(EnchantmentAPI plugin) {
        super(plugin, "EnchantAPI");
        HelpCommand help = new HelpCommand();
        this.registerCommand("help", help);
        this.registerCommand("?", help);
        this.registerCommand("reload", new ReloadCommand());
        this.registerCommand("list", new EnchantListCommand());
        this.registerCommand("add", new AddEnchantCommand());
        this.registerCommand("remove", new RemoveEnchantCommand());
        this.registerCommand("book", new BookCommand());
    }

    public boolean noArgs(CommandSender sender, Command command, String label) {
        if(!sender.isOp()) return true;

        sender.sendMessage(ChatColor.GRAY + "========= " + ChatColor.RED + this.plugin.getName() + ChatColor.GRAY + " =========");
        sender.sendMessage(" /enchantapi");
        sender.sendMessage("    help         " + ChatColor.YELLOW + "- Show the help menu");
        sender.sendMessage("    list          " + ChatColor.YELLOW + "- List all enchantments");
        sender.sendMessage("    list <page> " + ChatColor.YELLOW + "- List a page of enchantments with descriptions");
        sender.sendMessage("    book        " + ChatColor.YELLOW + "- Gives a book with enchantment descriptions");
        sender.sendMessage("    reload      " + ChatColor.YELLOW + "- Reload the plugin");
        sender.sendMessage("    add          " + ChatColor.YELLOW + "- Add an enchantment to an item");
        sender.sendMessage("    remove          " + ChatColor.YELLOW + "- Remove an enchantment from an item");

        return true;
    }

    public boolean unknownCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + this.plugin.getTag() + " Unknown command: " + ChatColor.WHITE + args[0]);
        return true;
    }

    private class HelpCommand implements ICommand {
        private HelpCommand() {
        }

        public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
            return Commander.this.noArgs(sender, command, label);
        }
    }
}
