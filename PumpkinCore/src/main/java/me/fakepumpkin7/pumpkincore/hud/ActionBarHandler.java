package me.fakepumpkin7.pumpkincore.hud;

import me.fakepumpkin7.pumpkinframework.combat.CombatUtils;
import me.fakepumpkin7.pumpkinframework.hud.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarHandler implements Runnable{



    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            String abs = getActionBarString(player);
            ActionBar.sendActionBar(player, abs);
        }
    }

    private String getActionBarString(Player player){
        long baseDmg = Math.round(CombatUtils.getEntityBaseDamage(player));
        long defence = Math.round(CombatUtils.getEntityDefence(player));
        long maxHealth = Math.round(CombatUtils.getEntityMaxHealth(player));
        long speed = Math.round(CombatUtils.getPlayerSpeed(player));

        String message = "" + ChatColor.AQUA + baseDmg + "\u2694 Damage " +ChatColor.GREEN + defence + "\u2748 Defence  " +  ChatColor.RED +maxHealth+ "\u2764 Max-Health  "+ ChatColor.WHITE +speed+ "\u2726 Speed  " ;
        return message;
    }



}
