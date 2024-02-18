package me.fakepumpkin7.pumpkinframework;

import de.tr7zw.nbtapi.NBTItem;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;

import java.util.List;

public class CombatUtils {

    public static long knockBackCooldownMS = 500;

    public static void dealDamage(Entity target, double finalDamage, double targetMaxHealth){

        if(target instanceof LivingEntity){
            LivingEntity le = (LivingEntity) target;

            double damageToDeal = finalDamage*le.getMaxHealth() / targetMaxHealth;
            le.damage(damageToDeal);
        }

        if(target instanceof Item){
            Item itemEntity = (Item) target;
            itemEntity.remove();
        }
    }

    public static void dealKnockback(Entity target, Entity attacker, double knockback){
        if(attacker == null){
            return;
        }

        if(target.hasMetadata("pumpkin-last-knockback")
                && ( System.currentTimeMillis() < target.getMetadata("pumpkin-last-knockback").get(0).asLong())) {
            return;
        }

        Vector distVec = target.getLocation().subtract(attacker.getLocation()).toVector();
        Vector normDistVec =  distVec.normalize().setY(0);
        Vector scaledDistVec = normDistVec.multiply(knockback);
        target.setVelocity( target.getVelocity().add (scaledDistVec) );

        //sets to when cooldown ends
        target.setMetadata("pumpkin-last-knockback", new FixedMetadataValue(PumpkinFramework.getInstance(), System.currentTimeMillis() + knockBackCooldownMS));
    }
    //DAMAGE UTILS

    public static double getEntityBaseDamage(Entity entity){
        double base = 0;

        if(entity.hasMetadata("pumpkin-base-damage")){
            base = entity.getMetadata("pumpkin-base-damage").get(0).asDouble();
        }

        return base;
    }
    public static double getEntityDamageMulti(Entity entity){
        double multi = 1;

        if(entity.hasMetadata("pumpkin-damage-multi")){
            multi = entity.getMetadata("pumpkin-damage-multi").get(0).asDouble();
        }

        return multi;
    }

    public static void setEntityBaseDamage(Entity entity, double base){
        entity.setMetadata("pumpkin-base-damage", new FixedMetadataValue(PumpkinFramework.getInstance(), base));
    }
    public static void setEntityDamageMulti(Entity entity, double multi){
        entity.setMetadata("pumpkin-damage-multi", new FixedMetadataValue(PumpkinFramework.getInstance(), multi));
    }

    public static void addEntityBaseDamage(Entity entity, double toAdd){
        double current = getEntityBaseDamage(entity);

        double newVal = current + toAdd;

        setEntityBaseDamage(entity, newVal);
    }
    public static void addEntityDamageMulti(Entity entity, double toAdd){
        double current = getEntityDamageMulti(entity);

        double newVal = current + toAdd;

        setEntityDamageMulti(entity, newVal);
    }


    public static double getItemBaseDamage(ItemStack item) {
        double base = 0;
        if(item.getType().equals(Material.AIR) || item == null){
            return base;
        }

        if (NbtUtil.hasNbt(item,"pumpkin-base-damage")) {
            base = NbtUtil.getNbtDouble(item, "pumpkin-base-damage");
        }

        return base;

    }
    public static double getItemDamageMulti(ItemStack item) {
        double multi = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return multi;
        }


        if (me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil.hasNbt(item,"pumpkin-damage-multi")) {
            multi = NbtUtil.getNbtDouble(item, "pumpkin-damage-multi");
        }

        return multi;
    }

    public static double getItemKnockbackMulti(ItemStack item) {
        double knockbackMulti = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return knockbackMulti;
        }


        if (NbtUtil.hasNbt(item,"pumpkin-knockback-multi")) {
            knockbackMulti = NbtUtil.getNbtDouble(item, "pumpkin-knockback-multi");
        }

        return knockbackMulti;
    }
    public static ItemStack setItemBaseDamage(ItemStack item, double base){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-base-damage", base);

        return nbtItem.getItem();

    }
    public static ItemStack setItemDamageMulti(ItemStack item, double multi){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-damage-multi", multi);

        return nbtItem.getItem();
    }

    public static ItemStack addItemBaseDamage(ItemStack item, double toAdd){
        double current = getItemBaseDamage(item);
        double newVal = current + toAdd;

        return setItemBaseDamage(item, newVal);
    }
    public static ItemStack addItemDamageMulti(ItemStack item, double toAdd){
        double current = getItemDamageMulti(item);
        double newVal = current + toAdd;

        return setItemDamageMulti(item, newVal);
    }



    //DEFENCE UTILS

    public static double getEntityDefence(Entity entity){
        double defence = 0;
        if(entity.hasMetadata("pumpkin-custom-defence")){
            defence = entity.getMetadata("pumpkin-custom-defence").get(0).asDouble();
        }
        return defence;
    }

    public static void setEntityDefence(Entity entity, double toSet){
        toSet = Math.max(0,toSet);
        entity.setMetadata("pumpkin-custom-defence", new FixedMetadataValue(PumpkinFramework.getInstance(), toSet));
    }

    public static void addEntityDefence(Entity entity, double toAdd){
        double current = getEntityDefence(entity);
        double newVal = current + toAdd;

        setEntityDefence(entity, newVal);
    }

    public static double getItemDefence(ItemStack itemStack){
        double defence = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-defence" )){
            defence = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-defence");
        }
        return defence;
    }

    public static void setItemDefence(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-defence",toSet);
    }
    public static void addItemDefence(ItemStack itemStack, double toAdd){
        double current = getItemDefence(itemStack);
        double newVal = current + toAdd;

        setItemDefence(itemStack, newVal);
    }

    //HEALTH UTILS

    public static double getEntityCustomHealth(Entity entity){
        double health = 1;
        if(entity instanceof LivingEntity){
            double vanillaMaxHealth = ((LivingEntity) entity).getMaxHealth();
            double vanillaHealth = ((LivingEntity) entity).getHealth();

            double maxHealth = getEntityMaxHealth(entity);
            health = (vanillaHealth/vanillaMaxHealth)*maxHealth;
        }
        return health;

    }

    public static double getEntityMaxHealth(Entity entity){
        double maxhealth = 1;

        if(entity.hasMetadata("pumpkin-custom-health")){
            maxhealth = entity.getMetadata("pumpkin-custom-health").get(0).asDouble();
        }

        return maxhealth;
    }

    public static void setEntityMaxHealth(Entity entity, double toSet){
        toSet = Math.max(1,toSet);
        entity.setMetadata("pumpkin-custom-health", new FixedMetadataValue(PumpkinFramework.getInstance(), toSet));
    }
    public static void addEntityHealth(Entity entity, double toAdd){
        double current = getEntityMaxHealth(entity);

        double newVal = current + toAdd;

        setEntityMaxHealth(entity, newVal);
    }

    public static double getItemMaxHealth(ItemStack itemStack){
        double maxhealth = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-health" )){
            maxhealth = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-health");
        }
        return maxhealth;
    }

    public static void setItemMaxHealth(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-health",toSet);
    }
    public static void addItemMaxHealth(ItemStack itemStack, double toAdd){
        double current = getItemMaxHealth(itemStack);
        double newVal = current + toAdd;

        setItemMaxHealth(itemStack, newVal);
    }

    //SPEED UTILS
    public static double getPlayerSpeed(Player player){
        double speed = player.getWalkSpeed();

        if(player.hasMetadata("pumpkin-custom-speed")){
            speed = player.getMetadata("pumpkin-custom-speed").get(0).asDouble();
        }

        return speed;
    }

    public static void setPlayerSpeed(Player player, double speed){
        player.setMetadata("pumpkin-custom-speed", new FixedMetadataValue(PumpkinFramework.getInstance(), speed));
    }

    public static void addPlayerSpeed(Player player, double toAdd){
        double current = getPlayerSpeed(player);

        double newVal = toAdd + current;

        setPlayerSpeed(player, newVal);
    }

    public static double getItemSpeed(ItemStack itemStack){
        double speed = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-speed" )){
            speed = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-speed");
        }
        return speed;
    }

    public static void setItemSpeed(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-speed",toSet);
    }

    public static void addItemSpeed(ItemStack itemStack, double toAdd){
        double current = getItemSpeed(itemStack);
        double newVal = current + toAdd;

        setItemSpeed(itemStack, newVal);
    }
}

