package me.fakepumpkin7.pumpkincombat.customcombat.speed.listener;

import me.fakepumpkin7.pumpkincombat.customcombat.speed.CustomSpeed;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;



public class SpeedArmorChangeListener implements Listener {
    CustomSpeed customSpeed;

    public SpeedArmorChangeListener(CustomSpeed customSpeed){
        this.customSpeed = customSpeed;
    }

    @EventHandler
    public void onPlayerAddArmor(ArmorEquipEvent event){

        //if piece put on has health stat, add health stat.
        ItemStack applied = event.getItem();
        Player entity = event.getPlayer();


        double appliedSpeed = CombatUtils.getItemSpeed(applied);
        CombatUtils.addPlayerSpeed(entity, appliedSpeed);
    }

    @EventHandler
    public void onPlayerRemoveArmor(ArmorUnEquipEvent event){
        //if piece removed had defence stat, remove defence stat.
        ItemStack removed = event.getItem();
        Player entity = event.getPlayer();


        double removedSpeed = CombatUtils.getItemSpeed(removed);
        CombatUtils.addPlayerSpeed(entity,  -1*removedSpeed );
    }

}