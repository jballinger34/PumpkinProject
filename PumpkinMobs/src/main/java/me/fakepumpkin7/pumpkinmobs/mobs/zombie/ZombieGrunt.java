package me.fakepumpkin7.pumpkinmobs.mobs.zombie;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.items.ItemBuilder;
import me.fakepumpkin7.pumpkinframework.mobs.CreatureBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class ZombieGrunt {

    EntityType entityType = EntityType.ZOMBIE;
    String name = ""+ ChatColor.DARK_GREEN + "Zombie " + ChatColor.AQUA+ "Grunt";
    int maxHealth = 20;
    ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS).build();
    ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).build();

    public ZombieGrunt(Location location, int level){

        CreatureBuilder zgBuilder = new CreatureBuilder(location, entityType);

        zgBuilder.setBoots(boots);
        zgBuilder.setChestplate(chestplate);

        zgBuilder.setMaxHealth(maxHealth);


        zgBuilder.setName(ChatColor.RED + "Lvl: "+ level +" " + name + " " + ChatColor.RED + maxHealth + "<3");



    }

}
