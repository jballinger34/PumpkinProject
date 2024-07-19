package me.fakepumpkin7.pumpkinframework.combat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DealDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onACDE(CustomDamageEvent event){
        if(event.isCancelled()) return;

        CombatUtils.dealTrueDamage(event.getTarget(), event.workOutDamageAfterDefence());
        CombatUtils.dealKnockback(event.getTarget(), event.getAttacker(), event.getKnockback());
    }
}
