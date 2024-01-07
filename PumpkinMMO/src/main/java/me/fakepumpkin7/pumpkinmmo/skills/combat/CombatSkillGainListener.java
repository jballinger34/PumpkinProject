package me.fakepumpkin7.pumpkinmmo.skills.combat;

import me.fakepumpkin7.pumpkinmmo.event.SkillExpGainEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CombatSkillGainListener implements Listener {

    // Listeners for a specific skill
    // will listen to vanilla events
    // then call the skill exp gain event
    // adding exp will be dealt with by this

    CombatSkill skill;

    public CombatSkillGainListener(CombatSkill skill){
        this.skill = skill;
    }
    @EventHandler
    public void TEST(PlayerJoinEvent e){
        Bukkit.getPluginManager().callEvent(new SkillExpGainEvent(e.getPlayer(), skill, 10));
    }

}
