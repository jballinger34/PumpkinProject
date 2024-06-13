package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.CustomEnchantment;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;

public class BaseEnchant extends CustomEnchantment {


    @Getter
    private EnchantmentGroup group;

    public BaseEnchant(String name, String description, int max, EnchantmentGroup group) {
        this(name, description ,group,max, false);
    }

    public BaseEnchant(String name, String description, EnchantmentGroup group, int max,boolean stacks) {
        super(name, description ,group.getAllMaterials(),max, stacks);
        this.group = group;
    }



}
