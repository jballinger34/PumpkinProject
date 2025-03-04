package me.fakepumpkin7.pumpkinmmo.listener;

import me.fakepumpkin7.pumpkinframework.mmo.SkillExpGainEvent;
import me.fakepumpkin7.pumpkinmmo.SkillHandler;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillExpGainListener implements Listener {

    SkillHandler skillHandler;

    public SkillExpGainListener(SkillHandler skillHandler){
        this.skillHandler = skillHandler;
    }

    @EventHandler
    public void onSkillExpGain(SkillExpGainEvent event){
        double exp = event.getExp();
        String skillName = event.getSkillName();
        Skill skill = skillHandler.getSkillFromName(skillName);
        Player player = event.getPlayer();
        double current = 0;

        if(skill.getExpMap().get(player.getUniqueId()) != null){
            current = skill.getExpMap().get(player.getUniqueId());
        }

        double newVal = current + exp;

        skill.getExpMap().put(player.getUniqueId(), newVal);
    }



}
