package me.fakepumpkin7.pumpkincombat.customcombat;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkincombat.customcombat.damage.event.CustomDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

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

    public static void dealKnockback(CustomDamageEvent e, double knockback){
        Entity target = e.getTarget();
        Entity attacker = e.getAttacker();

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
        target.setMetadata("pumpkin-last-knockback", new FixedMetadataValue(PumpkinCombat.getInstance(), System.currentTimeMillis() + knockBackCooldownMS));
    }
    //DAMAGE UTILS

    public double getEntityBaseDamage(Entity entity){
        double base = 0;

        if(entity.hasMetadata("pumpkin-base-damage")){
            base = entity.getMetadata("pumpkin-base-damage").get(0).asDouble();
        }

        return base;
    }
    public double getEntityDamageMulti(Entity entity){
        double multi = 1;

        if(entity.hasMetadata("pumpkin-damage-multi")){
            multi = entity.getMetadata("pumpkin-damage-multi").get(0).asDouble();
        }

        return multi;
    }

    public void setEntityBaseDamage(Entity entity, double base){
        entity.setMetadata("pumpkin-base-damage", new FixedMetadataValue(plugin, base));
    }
    public void setEntityDamageMulti(Entity entity, double multi){
        entity.setMetadata("pumpkin-damage-multi", new FixedMetadataValue(plugin, multi));
    }

    public void addEntityBaseDamage(Entity entity, double toAdd){
        double current = getEntityBaseDamage(entity);

        double newVal = current + toAdd;

        setEntityBaseDamage(entity, newVal);
    }
    public void addEntityDamageMulti(Entity entity, double toAdd){
        double current = getEntityDamageMulti(entity);

        double newVal = current + toAdd;

        setEntityDamageMulti(entity, newVal);
    }


    public double getItemBaseDamage(ItemStack item) {
        double base = 0;
        if(item.getType().equals(Material.AIR) || item == null){
            return base;
        }

        if (NbtUtil.hasNbt(item,"pumpkin-base-damage")) {
            base = NbtUtil.getNbtDouble(item, "pumpkin-base-damage");
        }

        return base;

    }
    public double getItemDamageMulti(ItemStack item) {
        double multi = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return multi;
        }


        if (me.fakepumpkin7.pumpkinframework.items.nbt.NbtUtil.hasNbt(item,"pumpkin-damage-multi")) {
            multi = NbtUtil.getNbtDouble(item, "pumpkin-damage-multi");
        }

        return multi;
    }

    public double getItemKnockbackMulti(ItemStack item) {
        double knockbackMulti = 1;

        if(item.getType().equals(Material.AIR) || item == null){
            return knockbackMulti;
        }


        if (NbtUtil.hasNbt(item,"pumpkin-knockback-multi")) {
            knockbackMulti = NbtUtil.getNbtDouble(item, "pumpkin-knockback-multi");
        }

        return knockbackMulti;
    }
    public ItemStack setItemBaseDamage(ItemStack item, double base){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-base-damage", base);

        return nbtItem.getItem();

    }
    public ItemStack setItemDamageMulti(ItemStack item, double multi){
        NBTItem nbtItem = NbtUtil.getNbtItem(item);
        nbtItem.setDouble("pumpkin-damage-multi", multi);

        return nbtItem.getItem();
    }

    public ItemStack addItemBaseDamage(ItemStack item, double toAdd){
        double current = getItemBaseDamage(item);
        double newVal = current + toAdd;

        return setItemBaseDamage(item, newVal);
    }
    public ItemStack addItemDamageMulti(ItemStack item, double toAdd){
        double current = getItemDamageMulti(item);
        double newVal = current + toAdd;

        return setItemDamageMulti(item, newVal);
    }



    //DEFENCE UTILS

    public double getEntityDefence(Entity entity){
        double defence = 0;
        if(entity.hasMetadata("pumpkin-custom-defence")){
            defence = entity.getMetadata("pumpkin-custom-defence").get(0).asDouble();
        }
        return defence;
    }

    public void setEntityDefence(Entity entity, double toSet){
        toSet = Math.max(0,toSet);
        entity.setMetadata("pumpkin-custom-defence", new FixedMetadataValue(plugin, toSet));
    }

    public void addEntityDefence(Entity entity, double toAdd){
        double current = getEntityDefence(entity);
        double newVal = current + toAdd;

        setEntityDefence(entity, newVal);
    }

    public double getItemDefence(ItemStack itemStack){
        double defence = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-defence" )){
            defence = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-defence");
        }
        return defence;
    }

    public void setItemDefence(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-defence",toSet);
    }
    public void addItemDefence(ItemStack itemStack, double toAdd){
        double current = getItemDefence(itemStack);
        double newVal = current + toAdd;

        setItemDefence(itemStack, newVal);
    }

    //HEALTH UTILS

    public double getEntityHealth(Entity entity){
        double health = 20;

        if(entity.hasMetadata("pumpkin-custom-health")){
            health = entity.getMetadata("pumpkin-custom-health").get(0).asDouble();
        }

        return health;
    }

    public void setEntityHealth(Entity entity, double toSet){
        toSet = Math.max(0,toSet);
        entity.setMetadata("pumpkin-custom-health", new FixedMetadataValue(plugin, toSet));
    }
    public void addEntityHealth(Entity entity, double toAdd){
        double current = getEntityHealth(entity);

        double newVal = current + toAdd;

        setEntityHealth(entity, newVal);
    }

    public double getItemHealth(ItemStack itemStack){
        double health = 0;
        if(NbtUtil.hasNbt(itemStack,"pumpkin-custom-health" )){
            health = NbtUtil.getNbtDouble(itemStack,"pumpkin-custom-health");
        }
        return health;
    }

    public void setItemHealth(ItemStack itemStack, double toSet){
        NbtUtil.addNbt(itemStack,"pumpkin-custom-health",toSet);
    }
    public void addItemHealth(ItemStack itemStack, double toAdd){
        double current = getItemHealth(itemStack);
        double newVal = current + toAdd;

        setItemHealth(itemStack, newVal);
    }

    //SPEED UTILS
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

