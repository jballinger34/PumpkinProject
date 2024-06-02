//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.commands;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.enchanting.EEnchantTable;
import com.rit.sucy.enchanting.VanillaEnchantment;
import com.rit.sucy.service.ENameParser;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class StatTask extends BukkitRunnable {
    private static final DecimalFormat format = new DecimalFormat("##0.0#");
    private final ItemStack item;
    private final CommandSender sender;
    private final int level;
    private final int maxEnchants;

    public StatTask(CommandSender sender, ItemStack item, int level) {
        this.sender = sender;
        this.level = level;
        this.item = item;
        this.maxEnchants = ((RootConfig)((EnchantmentAPI)Bukkit.getPluginManager().getPlugin("EnchantmentAPI")).getModuleForClass(RootConfig.class)).getInt(RootNode.MAX_ENCHANTS);
    }

    public void run() {
        List<CustomEnchantment> validEnchants = EnchantmentAPI.getAllValidEnchants(this.item);
        Hashtable<String, int[]> data = new Hashtable();

        for(int i = 0; i < 100000; ++i) {
            Map<CustomEnchantment, Integer> list = EEnchantTable.enchant((Player)null, this.item, this.level, this.maxEnchants, false).getAddedEnchants();

            Map.Entry entry;
            int[] values;
            for(Iterator var5 = list.entrySet().iterator(); var5.hasNext(); ++values[(Integer)entry.getValue() - 1]) {
                entry = (Map.Entry)var5.next();
                String name = ((CustomEnchantment)entry.getKey()).name();
                if (!data.containsKey(name)) {
                    data.put(name, new int[((CustomEnchantment)entry.getKey()).getMaxLevel()]);
                }

                values = (int[])data.get(name);
            }
        }

        this.sender.sendMessage(ChatColor.GOLD + this.item.getType().name() + ChatColor.DARK_GREEN + " - Enchantment Stats (Lv " + this.level + ")");
        Iterator var9 = validEnchants.iterator();

        while(true) {
            CustomEnchantment enchant;
            do {
                if (!var9.hasNext()) {
                    return;
                }

                enchant = (CustomEnchantment)var9.next();
            } while(enchant.getMaxLevel() == 0);

            String message = enchant.name() + " (";
            if (enchant instanceof VanillaEnchantment) {
                message = ENameParser.getVanillaName(((VanillaEnchantment)enchant).getVanillaEnchant()) + " (";
            }

            if (data.containsKey(enchant.name())) {
                int[] values = (int[])data.get(enchant.name());
                boolean index = false;

                for(int i = 0; i < enchant.getMaxLevel(); ++i) {
                    message = message + ChatColor.GOLD + format.format((double)values[i] / 1000.0) + "%" + ChatColor.DARK_GREEN + ", ";
                }
            } else {
                for(int i = 1; i <= enchant.getMaxLevel(); ++i) {
                    message = message + ChatColor.GOLD + "0.0%" + ChatColor.DARK_GREEN + ", ";
                }
            }

            this.sender.sendMessage(ChatColor.DARK_GREEN + message.substring(0, message.length() - 2) + ")");
        }
    }
}
