package me.fakepumpkin7.pumpkinframework.combat;

import de.tr7zw.nbtapi.plugin.tests.items.EmptyItemTest;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.chat.ChatUtils;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class CombatUtils {

    //TODO
    // ADD A METHOD TO DEAL A CERTAIN DAMAGE BEFORE ARMOUR AND ENCHANTS AND STUFF.

    // truedamage does the opposite, deals a set no of damage.
    public static void dealTrueDamage(Entity target, double finalDamage){

        if(target instanceof LivingEntity){
            LivingEntity le = (LivingEntity) target;

            le.damage(finalDamage);
        }

        if(target instanceof Item){
            Item itemEntity = (Item) target;
            itemEntity.remove();
        }
    }
    public static void healEntity(Entity target, double healAmount){
        if(target instanceof LivingEntity){

            LivingEntity le = (LivingEntity) target;

            double newHealth = Math.min(le.getMaxHealth(), le.getHealth() + healAmount);
            le.setHealth(newHealth);
        }
    }


    public static boolean isStun(Player player){
        if(player.hasMetadata("pumpkin-stun") && player.getMetadata("pumpkin-stun").get(0).asLong() >= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    public static void addStun(Player player, double seconds){
        long currentStun = System.currentTimeMillis();
        long stunToAdd = (long) (seconds*1000);
        if(CombatUtils.isStun(player)){
            currentStun = player.getMetadata("pumpkin-stun").get(0).asLong();
        }
        player.setMetadata("pumpkin-stun",new FixedMetadataValue(PumpkinFramework.getInstance(), currentStun + stunToAdd));
    }


}