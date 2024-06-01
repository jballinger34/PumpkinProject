package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkinframework.event.combat.CustomDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamagedNotByEntity(EntityDamageEvent e){
        if(causedByEntity(e.getCause())){
            return;
        }

        Bukkit.getPluginManager().callEvent( new CustomDamageEvent(e.getCause(),null, e.getEntity(), e.getDamage()) );

        e.setDamage(0);
        e.setCancelled(true);

    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e){
        Bukkit.getPluginManager().callEvent(  new CustomDamageEvent(e.getCause(), e.getDamager(), e.getEntity(), e.getDamage()) );

        e.setDamage(0);
        e.setCancelled(true);
    }

    private boolean causedByEntity(EntityDamageEvent.DamageCause cause){
        if(cause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) ||
                cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                cause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            return true;
        }
        return false;
    }

}
