package me.fakepumpkin7.pumpkincombat.customcombat.defence.listeners;

import me.fakepumpkin7.pumpkincombat.customcombat.defence.CustomDefence;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;

import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;


public class DefenceArmorChangeListener implements Listener {


    CustomDefence customDefence;


    public DefenceArmorChangeListener(CustomDefence customDefence){
        this.customDefence = customDefence;
    }

    @EventHandler
    public void onPlayerAddArmor(ArmorEquipEvent event){

        //if piece put on has defence stat, add defence stat.
        ItemStack applied = event.getItem();
        Player entity = event.getPlayer();


        double appliedDefence = CombatUtils.getItemDefence(applied);
        CombatUtils.addEntityDefence(entity, appliedDefence);
    }

    @EventHandler
    public void onPlayerRemoveArmor(ArmorUnEquipEvent event){
        //if piece removed had defence stat, remove defence stat.
        ItemStack removed = event.getItem();
        Player entity = event.getPlayer();


        double removedDefence = CombatUtils.getItemDefence(removed);
        CombatUtils.addEntityDefence(entity,  -1*removedDefence );
    }



}
