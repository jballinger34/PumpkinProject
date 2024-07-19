package me.fakepumpkin7.pumpkincombat.customcombat.health.listener;

import me.fakepumpkin7.pumpkincombat.customcombat.health.CustomHealth;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class HealthArmorChangeListener implements Listener {

    CustomHealth customHealth;

    public HealthArmorChangeListener(CustomHealth customHealth){
        this.customHealth = customHealth;
    }

    @EventHandler
    public void onPlayerAddArmor(ArmorEquipEvent event){

        //if piece put on has health stat, add health stat.
        ItemStack applied = event.getItem();
        Player entity = event.getPlayer();


        double appliedHealth = CombatUtils.getItemMaxHealth(applied);
        CombatUtils.addEntityHealth(entity, appliedHealth);
    }

    @EventHandler
    public void onPlayerRemoveArmor(ArmorUnEquipEvent event){
        //if piece removed had defence stat, remove defence stat.

        ItemStack removed = event.getItem();
        Player entity = event.getPlayer();


        double removedHealth = CombatUtils.getItemMaxHealth(removed);
        CombatUtils.addEntityHealth(entity,  -1*removedHealth );
    }

}
