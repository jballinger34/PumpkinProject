package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkincombat.customcombat.damage.CustomDamage;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorEquipEvent;
import me.fakepumpkin7.pumpkinframework.armor.events.ArmorUnEquipEvent;
import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class DamageArmorChangeListener implements Listener {
    CustomDamage customDamage;


    public DamageArmorChangeListener(CustomDamage customDamage){
        this.customDamage = customDamage;
    }

    @EventHandler
    public void onPlayerAddArmor(ArmorEquipEvent event){

        //if piece put on has defence stat, add defence stat.
        ItemStack applied = event.getItem();
        Player entity = event.getPlayer();


        double appliedBaseDamage = CombatUtils.getItemBaseDamage(applied);
        CombatUtils.addEntityBaseDamage(entity, appliedBaseDamage);
    }

    @EventHandler
    public void onPlayerRemoveArmor(ArmorUnEquipEvent event){
        //if piece removed had defence stat, remove defence stat.
        ItemStack removed = event.getItem();
        Player entity = event.getPlayer();


        double removedBaseDamage = CombatUtils.getItemBaseDamage(removed);
        CombatUtils.addEntityBaseDamage(entity,  -1*removedBaseDamage );
    }

}
