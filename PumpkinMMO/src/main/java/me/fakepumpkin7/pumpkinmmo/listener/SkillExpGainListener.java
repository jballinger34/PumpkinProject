package me.fakepumpkin7.pumpkinmmo.listener;

import me.fakepumpkin7.pumpkinmmo.event.SkillExpGainEvent;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillExpGainListener implements Listener {

    @EventHandler
    public void onSkillExpGain(SkillExpGainEvent event){
        double exp = event.getExp();
        Skill skill = event.getSkill();
        Player player = event.getPlayer();
        double current = 0;

        if(skill.getExpMap().get(player.getUniqueId()) != null){
            current = skill.getExpMap().get(player.getUniqueId());
        }

        double newVal = current + exp;

        skill.getExpMap().put(player.getUniqueId(), newVal);
    }



}
