package me.fakepumpkin7.pumpkinframework.combat;

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

    public static long knockBackCooldownMS = 500;


    //this will not call the CustomDamageEvent, so defence and any enchants will be ignored
    public static void dealTrueDamage(Entity target, double finalDamage){

        if(target instanceof LivingEntity){
            LivingEntity le = (LivingEntity) target;

            double damageToDeal = finalDamage*le.getMaxHealth() / getEntityMaxHealth(target);
            le.damage(damageToDeal);

            if (target instanceof Player) {
                Player player = (Player) target;
                ChatUtils.info(player,"Health stat " + getEntityCustomHealth(player));
                ChatUtils.info(player,"Hearts stat " + player.getHealth());

            }

        }

        if(target instanceof Item){
            Item itemEntity = (Item) target;
            itemEntity.remove();
        }
    }
    public static void healEntity(Entity target, double healAmount){
       if(target instanceof LivingEntity){
           LivingEntity le = (LivingEntity) target;
           double scaledHeal = healAmount*le.getMaxHealth() / getEntityMaxHealth(target);

           double newHealth = Math.min(le.getMaxHealth(), le.getHealth() + scaledHeal);
           le.setHealth(newHealth);
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
    public static void setEntityBaseDamage(Entity entity, double base){
        entity.setMetadata("pumpkin-base-damage", new FixedMetadataValue(PumpkinFramework.getInstance(), base));
    }

    public static void addEntityBaseDamage(Entity entity, double toAdd){
        double current = getEntityBaseDamage(entity);

        double newVal = current + toAdd;

        setEntityBaseDamage(entity, newVal);
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

}

