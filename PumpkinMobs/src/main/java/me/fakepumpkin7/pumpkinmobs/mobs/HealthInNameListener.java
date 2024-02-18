package me.fakepumpkin7.pumpkinmobs.mobs;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.event.CustomDamageEvent;
import me.fakepumpkin7.pumpkinframework.mobs.CreatureBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class HealthInNameListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(CustomDamageEvent e){
        if(e.getTarget() instanceof Creature){
            Creature creature = (Creature) e.getTarget();

            double health = CombatUtils.getEntityCustomHealth(creature);
            System.out.println("health: " + health);

            String oldName = creature.getName();
            String[] strList = oldName.split(" ");
            String newName = "";
            //purposely leaves out last element, this removes health from name, added later
            for(int i = 0; i<strList.length - 1; i++){
                newName += strList[i] + " ";
            }

            //adds health back
            new CreatureBuilder(creature).setName(newName+ ChatColor.RED + health + "<3");




        }
    }


}
