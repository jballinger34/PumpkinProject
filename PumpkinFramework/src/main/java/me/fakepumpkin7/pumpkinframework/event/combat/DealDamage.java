package me.fakepumpkin7.pumpkinframework.event.combat;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DealDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onACDE(AbstractCustomDamageEvent event){
        if(event.isCancelled()) return;

        CombatUtils.dealTrueDamage(event.getTarget(), event.getFinalDamage());
        if(event instanceof CustomDamageEvent){
            CustomDamageEvent cde = (CustomDamageEvent) event;
            CombatUtils.dealKnockback(cde.getTarget(), cde.getAttacker(), cde.getKnockback());
        }


    }


}
