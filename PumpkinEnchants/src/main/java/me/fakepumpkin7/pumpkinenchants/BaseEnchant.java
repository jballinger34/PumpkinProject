package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;

public class BaseEnchant extends CustomEnchantment {


    public BaseEnchant(String name, EnchantmentGroup group) {
        super(name, group.getAllMaterials());
    }


}
