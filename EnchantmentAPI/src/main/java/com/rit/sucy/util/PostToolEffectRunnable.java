package com.rit.sucy.util;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

public interface PostToolEffectRunnable {
    void execute(Player var1, Block var2, List<ItemStack> var3, BlockEvent var4);
}
