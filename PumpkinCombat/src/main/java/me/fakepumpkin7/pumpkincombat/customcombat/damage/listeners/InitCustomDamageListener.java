package me.fakepumpkin7.pumpkincombat.customcombat.damage.listeners;

import me.fakepumpkin7.pumpkincombat.PumpkinCombat;
import me.fakepumpkin7.pumpkinframework.CombatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InitCustomDamageListener implements Listener {

    PumpkinCombat plugin;

    public InitCustomDamageListener(PumpkinCombat p){
        this.plugin = p;
    }

    @EventHandler
    public void initCustomDamageOnPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        CombatUtils.setEntityBaseDamage(player,1);
    }



}
