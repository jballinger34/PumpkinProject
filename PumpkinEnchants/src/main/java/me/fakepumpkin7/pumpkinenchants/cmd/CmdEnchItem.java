package me.fakepumpkin7.pumpkinenchants.cmd;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.fakepumpkin7.pumpkinenchants.BaseEnchant;
import me.fakepumpkin7.pumpkinenchants.EnchantItem;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.player.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CmdEnchItem implements CommandExecutor {

    private final Random random = new Random();

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        if (!(sender.isOp())) {
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1 && args.length != 2) {
            ChatUtils.info(player, "/enchitem <enchant> <optional:level>");
            return true;
        }


        CustomEnchantment ce = EnchantmentAPI.getEnchantment(args[0].replace("_", " "));
        if (!(ce instanceof BaseEnchant)) {
            ChatUtils.info(player, "Enchantment " + args[0] + " is not a supported Enchantment.");
            return true;
        }
        BaseEnchant enchantment = (BaseEnchant) ce;

        int level = -1;
        if(args.length == 2){
            //try get level from 2nd arg
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                level = random.nextInt(enchantment.getMaxLevel()) + 1;
            }
        }
        if(level == -1){
            level = random.nextInt(enchantment.getMaxLevel()) + 1;
        }


        ItemStack toAdd = EnchantItem.getInstance().getEnchantItem(enchantment, level);

        PlayerUtils.addItems(player,toAdd);
        return true;
    }

}
