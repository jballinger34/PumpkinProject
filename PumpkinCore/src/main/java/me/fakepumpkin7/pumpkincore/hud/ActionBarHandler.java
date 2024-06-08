package me.fakepumpkin7.pumpkincore.hud;

import me.fakepumpkin7.pumpkinframework.CombatUtils;
import me.fakepumpkin7.pumpkinframework.hud.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarHandler implements Runnable{



    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            ActionBar actionBar = new ActionBar();

            String abs = getActionBarString(player);
            actionBar.sendActionBar(player, abs);
        }
    }

    private String getActionBarString(Player player){
        long baseDmg = Math.round(CombatUtils.getEntityBaseDamage(player));
        long defence = Math.round(CombatUtils.getEntityDefence(player));
        long maxHealth = Math.round(CombatUtils.getEntityMaxHealth(player));
        long speed = Math.round(CombatUtils.getPlayerSpeed(player));

        String message = "Base Damage: " + baseDmg + ", Defence: " + defence + ", Max Health: "+ maxHealth + ", Speed: " + speed;
        return message;
    }



}
