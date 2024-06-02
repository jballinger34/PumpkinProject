//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ICommand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookCommand implements ICommand {
    public BookCommand() {
    }

    public boolean execute(EnchantmentAPI plugin, CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else {
            ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
            BookMeta meta = (BookMeta)book.getItemMeta();
            meta.addPage(new String[]{"EnchantmentAPI\nMade by Steven Sucy\n(Eniripsa96)\n\n Enchantment details \n\nCommand:\n\n/enchantapi list #"});
            meta.setAuthor("Eniripsa96");
            meta.setTitle("EnchantmentAPI");
            ArrayList<CustomEnchantment> enchants = new ArrayList(EnchantmentAPI.getEnchantments());
            Collections.sort(enchants);
            Iterator var9 = enchants.iterator();

            while(true) {
                CustomEnchantment enchantment;
                do {
                    if (!var9.hasNext()) {
                        book.setItemMeta(meta);
                        ((Player)sender).getInventory().addItem(new ItemStack[]{book});
                        return true;
                    }

                    enchantment = (CustomEnchantment)var9.next();
                } while(enchantment.getDescription() == null);


                String page = enchantment.name() + " - " + enchantment.getDescription() + "\n\nItems: ";
                if (enchantment.getNaturalMaterials().length <= 0) {
                    page = page + "None";
                } else {
                    Material[] var12 = enchantment.getNaturalMaterials();
                    int var13 = var12.length;

                    for(int var14 = 0; var14 < var13; ++var14) {
                        Material item = var12[var14];
                        page = page + ChatColor.stripColor(ENameParser.getName(item)) + ", ";
                    }

                    page = page.substring(0, page.length() - 2);
                }

                meta.addPage(new String[]{page});
            }
        }
    }
}
