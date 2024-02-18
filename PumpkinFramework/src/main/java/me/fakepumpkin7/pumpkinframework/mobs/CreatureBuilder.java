package me.fakepumpkin7.pumpkinframework.mobs;


import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

public class CreatureBuilder{
    Creature creature;
    public CreatureBuilder(Location location, EntityType type){
        this.creature = (Creature) location.getWorld().spawnEntity(location,type);
    }
    public CreatureBuilder(Creature creature){
        this.creature = creature;
    }

    public Creature build(){
        return creature;
    }

    public CreatureBuilder setDrops(LootPool lootPool){
        creature.setMetadata("pumpkin-mobs-drops", new FixedMetadataValue(PumpkinFramework.getInstance(), lootPool));
        return this;
    }
    public CreatureBuilder setSkillExp(String skillName, double exp){
        creature.setMetadata("pumpkin-"+skillName.toLowerCase()+"-exp", new FixedMetadataValue(PumpkinFramework.getInstance(), exp));
        return this;
    }
    public CreatureBuilder setName(String name){
        creature.setCustomName(name);
        return this;
    }
    public CreatureBuilder setHelmet(ItemStack helmet){
        creature.getEquipment().setHelmet(helmet);
        return this;
    }
    public CreatureBuilder setChestplate(ItemStack chestplate){
        creature.getEquipment().setChestplate(chestplate);
        return this;
    }
    public CreatureBuilder setLeggings(ItemStack leggings){
        creature.getEquipment().setLeggings(leggings);
        return this;
    }
    public CreatureBuilder setBoots(ItemStack boots){
        creature.getEquipment().setBoots(boots);
        return this;
    }
    public CreatureBuilder setItemInMainHand(ItemStack item){
        creature.getEquipment().setItemInHand(item);
        return this;
    }
    public CreatureBuilder setMaxHealth(double maxHealth){
        CombatUtils.setEntityMaxHealth(creature, maxHealth);
        return this;
    }
    public CreatureBuilder setDefence(double defence){
        CombatUtils.setEntityDefence(creature, defence);
        return this;
    }
    public CreatureBuilder setBaseDamage(double damage){
        CombatUtils.setEntityBaseDamage(creature, damage);
        return this;
    }


}
