package me.fakepumpkin7.pumpkincombat.customcombat.speed;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.listener.InitCustomSpeedListener;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.listener.SpeedArmorChangeListener;
import me.fakepumpkin7.pumpkincombat.customcombat.speed.tasks.UpdateCustomSpeedTask;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class CustomSpeed {

    PumpkinCombat plugin;

    public CustomSpeed(PumpkinCombat plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InitCustomSpeedListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new SpeedArmorChangeListener(this), plugin);

        UpdateCustomSpeedTask speedTask = new UpdateCustomSpeedTask(plugin);
        speedTask.runTaskTimer(plugin, 20, 40);

    }
}
