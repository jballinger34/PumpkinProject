package me.fakepumpkin7.pumpkineconomy.listener;

import me.fakepumpkin7.pumpkineconomy.Bank;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MoneyNoteListener implements Listener {
    @EventHandler
    public void onMoneyNoteUse(PlayerInteractEvent event){
        if(event.getItem() == null){
            return;
        }
        ItemStack item = event.getItem();

        if(NbtUtil.hasNbt(item, Bank.moneyNoteNBT)){
            Player player = event.getPlayer();

            double moneyValue = NbtUtil.getNbtDouble(item, Bank.moneyNoteNBT);
            int stackSize = item.getAmount();

            item.setAmount(stackSize - 1);
            player.setItemInHand(item);

            Bank.getInstance().addBalance(player.getUniqueId(), moneyValue);
            ChatUtils.notify(player, "Redeemed money note worth " + moneyValue);
        }
    }
}
