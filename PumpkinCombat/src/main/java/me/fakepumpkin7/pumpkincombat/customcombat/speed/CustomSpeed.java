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


    public double getPlayerSpeed(Player player){
        double speed = player.getWalkSpeed();

        if(player.hasMetadata("pumpkin-custom-speed")){
            speed = player.getMetadata("pumpkin-custom-speed").get(0).asDouble();
        }

        return speed;
    }

    public void setPlayerSpeed(Player player, double speed){
        player.setMetadata("pumpkin-custom-speed", new FixedMetadataValue(plugin, speed));
    }

    public void addPlayerSpeed(Player player, double toAdd){
        double current = getPlayerSpeed(player);

        double newVal = toAdd + current;

        setPlayerSpeed(player, newVal);
    }

    public double getItemSpeed(ItemStack itemStack){
        double speed = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-speed" )){
            speed = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-speed");
        }
        return speed;
    }

    public void setItemSpeed(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-speed",toSet);
    }

    public void addItemSpeed(ItemStack itemStack, double toAdd){
        double current = getItemSpeed(itemStack);
        double newVal = current + toAdd;

        setItemSpeed(itemStack, newVal);
    }

}
