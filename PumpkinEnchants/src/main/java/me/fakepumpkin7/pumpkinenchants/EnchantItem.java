package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnchantItem {

    private static final Random random = new Random();
    public static final Material enchantMaterial = Material.BOOK;
    public static final String enchantNBT = "isEnchItem";
    public static final int maxEnchantsOnItem = 8;

    public static ItemStack getEnchantItem(CustomEnchantment ench, int level) {
        return getEnchantItem(ench, level, random.nextInt(100), random.nextInt(100));
    }

    public static ItemStack getEnchantItem(CustomEnchantment enchantment, int level, int successRate, int destroyRate) {
        if (level == 0) {
            level = 1;
        }

        final ChatColor COLOUR = EnchantType.getEnchantmentTypeFromName(enchantment.name()).getRarity().getColor();

        ItemBuilder builder = new ItemBuilder(enchantMaterial)
                .setName(ChatColor.YELLOW + "Rune of " + COLOUR + ChatColor.BOLD + ChatColor.UNDERLINE + enchantment.name() + " " + ERomanNumeral.numeralOf(level))
                .addLoreLine(ChatColor.GREEN.toString() + successRate + "%" + ChatColor.GREEN + " Success Rate")
                .addLoreLine(ChatColor.RED.toString() + destroyRate + "%" + ChatColor.RED + " Destroy Rate.")
                .addLoreLine( ChatColor.YELLOW.toString() + "Level " + ChatColor.GOLD + level)
                .addNBT(enchantNBT, "true" );



        String desc = enchantment.getDescription();
        for (String s : formatLore(desc)) {
            builder.addLoreLine(s);
        }
        builder.addLoreLine(ChatColor.GRAY + ((BaseEnchant) enchantment).getGroup().getName() + " Enchantment");
        builder.addLoreLine(ChatColor.GRAY + "Place rune on item to enchant.");


        return builder.build();
    }


    public static int getEnchantmentRuneSuccessRate(ItemStack is) {
        try {
            return Integer.parseInt(ChatColor.stripColor(is.getItemMeta().getLore().get(0).split(" ")[0].replace("%", "")));
        } catch (Exception err) {
            // Old book.
            return 100;
        }
    }

    public static int getEnchantmentRuneDestructionRate(ItemStack is) {
        try {
            return Integer.parseInt(ChatColor.stripColor(is.getItemMeta().getLore().get(1).split(" ")[0].replace("%", "")));
        } catch (Exception err) {
            // Old book.
            return 0;
        }
    }

    public static boolean isEnchantItem(ItemStack is) {
        return NbtUtil.hasNbt(is,enchantNBT);
    }

    public static CustomEnchantment getEnchantment(ItemStack ench_book) {
        if (!isEnchantItem(ench_book)) {
            return null;
        }

        String displayName = ench_book.getItemMeta().getDisplayName();
        String enchName = ChatColor.stripColor(displayName.split(ChatColor.UNDERLINE.toString())[1]);
        enchName = enchName.substring(0, enchName.lastIndexOf(" "));

        return EnchantmentAPI.getEnchantment(enchName);
    }

    public static int getEnchantmentLevel(ItemStack ench_book) {
        if (!isEnchantItem(ench_book)) {
            return 0;
        }

        return Integer.parseInt(ChatColor.stripColor(ench_book.getItemMeta().getLore().get(2).split(" ")[1]));
    }

    public static boolean applyEnchantmentRune(Player player, ItemStack ench_book, ItemStack item) {

        CustomEnchantment ce = getEnchantment(ench_book);
        int level = getEnchantmentLevel(ench_book);
        int success_rate = getEnchantmentRuneSuccessRate(ench_book);
        int destroy_rate = (int) (getEnchantmentRuneDestructionRate(ench_book) * 1.10D);

        if (ce == null || level == 0) {
            return false;
        }
        if (!ce.canEnchantOnto(item) || ench_book.getType() == Material.ENCHANTED_BOOK) {
            return false;
        }

        if (random.nextInt(100) > (success_rate)) {
            if (random.nextInt(100) < destroy_rate) {

                // Destroy.
                ChatUtils.warn(player,"Your item was destroyed in the enchanting process.");

                // Make the item "unique" for removal.
                item.setDurability((short) 69);
                player.getOpenInventory().getTopInventory().remove(item);
                player.getOpenInventory().getBottomInventory().remove(item);
            } else {
                ChatUtils.warn(player,"The enchantment rune failed to apply, yet your item remained unscathed.");
            }

            return false;
        }

        ce.addToItem(item, level);
        return true;
    }


    private static List<String> formatLore(String s) {
        List<String> lore_l = new ArrayList<String>();
        if (s == null) {
            return lore_l;
        }

        if (s.length() <= 38) {
            // Too short to care.
            lore_l.add(ChatColor.YELLOW + s);
            return lore_l;
        }

        if (s.contains(" ")) {
            String line = ChatColor.YELLOW.toString();
            for (String word : s.split(" ")) {
                if (((line.length() - 1) + word.length()) <= 38) {
                    // Good.
                    line += word + " ";
                } else {
                    // It's getting too big!
                    if (line.endsWith(" ")) {
                        line = line.substring(0, line.length() - 1);
                    }

                    lore_l.add(line);
                    line = ChatColor.YELLOW + word + " ";
                }
            }

            if (!ChatColor.stripColor(line).isEmpty()) {
                // Add remaining words.
                lore_l.add(line);
            }

        } else {
            // Why is it no spaces and > 38 characters lol...
            lore_l.add(s);
            return lore_l;
        }

        return lore_l;
    }
}
