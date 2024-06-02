/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.rit.sucy.commands;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.enchanting.EEnchantTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Map;

public class GraphTask
        extends BukkitRunnable {
    private static final DecimalFormat format = new DecimalFormat("##0.0");
    private final CommandSender sender;
    private final ItemStack item;
    private final CustomEnchantment enchant;
    private final int maxEnchants;

    public GraphTask(CommandSender sender, ItemStack item, CustomEnchantment enchant) {
        this.sender = sender;
        this.item = item;
        this.enchant = enchant;
        this.maxEnchants = ((EnchantmentAPI) Bukkit.getPluginManager().getPlugin("EnchantmentAPI")).getModuleForClass(RootConfig.class).getInt(RootNode.MAX_ENCHANTS);
    }

    public void run() {
        int k;
        Hashtable<String, int[]> points = new Hashtable<String, int[]>();
        for (int i = 1; i <= 30; ++i) {
            points.put(this.enchant.name() + i, new int[this.enchant.getMaxLevel()]);
        }
        for (int j = 1; j <= 30; ++j) {
            for (k = 0; k < 100000; ++k) {
                Map<CustomEnchantment, Integer> list = EEnchantTable.enchant(null, this.item, j, this.maxEnchants, false).getAddedEnchants();
                for (Map.Entry<CustomEnchantment, Integer> entry : list.entrySet()) {
                    int n;
                    int[] array;
                    if (!entry.getKey().equals(this.enchant)) continue;
                    int[] values = array = points.get(this.enchant.name() + j);
                    int n2 = n = entry.getValue() - 1;
                    array[n2] = array[n2] + 1;
                }
            }
        }
        int max = 0;
        for (int l = 1; l <= 30; ++l) {
            int[] data = points.get(this.enchant.name() + l);
            if (data == null) continue;
            for (int m = 0; m < this.enchant.getMaxLevel(); ++m) {
                if (data[m] <= max) continue;
                max = data[m];
            }
        }
        max = (max + 999) / 1000;
        for (k = 9; k >= 0; --k) {
            ChatColor lc = k == 0 ? ChatColor.GRAY : ChatColor.DARK_GRAY;
            String line = ChatColor.GOLD + "" + format.format((double) (k * max) / 10.0) + "-" + format.format((double) ((k + 1) * max) / 10.0) + "%" + lc + "_";
            while (line.length() < 16) {
                line = line + "_";
            }
            line = line + ChatColor.GRAY + "|";
            for (int j2 = 1; j2 <= 30; ++j2) {
                int[] data2 = points.get(this.enchant.name() + j2);
                String piece = lc + "_";
                for (int k2 = 0; k2 < this.enchant.getMaxLevel(); ++k2) {
                    if (data2[k2] <= k * max * 100 || data2[k2] > (k + 1) * max * 100) continue;
                    piece = ChatColor.getByChar((char) (49 + k2 % 6)) + "X";
                }
                line = line + piece;
            }
            this.sender.sendMessage(line);
        }
        this.sender.sendMessage(ChatColor.DARK_GRAY + "||__________" + ChatColor.GRAY + "|" + ChatColor.DARK_GRAY + "____" + ChatColor.GRAY + "5" + ChatColor.DARK_GRAY + "____" + ChatColor.GRAY + "10" + ChatColor.DARK_GRAY + "___" + ChatColor.GRAY + "15" + ChatColor.DARK_GRAY + "___" + ChatColor.GRAY + "20" + ChatColor.DARK_GRAY + "___" + ChatColor.GRAY + "25" + ChatColor.DARK_GRAY + "___" + ChatColor.GRAY + "30");
    }
}

