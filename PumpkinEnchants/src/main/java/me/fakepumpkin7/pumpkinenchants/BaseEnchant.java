package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;

public class BaseEnchant extends CustomEnchantment {


    public BaseEnchant(String name, String description, EnchantmentGroup group, int max,boolean stacks) {
        super(name, description ,group.getAllMaterials(),max, stacks);
    }
    public BaseEnchant(String name, String description, int max, EnchantmentGroup group) {
        super(name, description ,group.getAllMaterials(),max, false);
    }



}
