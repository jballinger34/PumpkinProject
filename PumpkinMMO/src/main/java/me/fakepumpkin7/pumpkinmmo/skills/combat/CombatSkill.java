package me.fakepumpkin7.pumpkinmmo.skills.combat;


import me.fakepumpkin7.pumpkinmmo.PumpkinMMO;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;
import org.bukkit.Bukkit;

public class CombatSkill extends Skill {

    public CombatSkill(){
        super("Combat", 50);

        Bukkit.getPluginManager().registerEvents(new CombatSkillGainListener(this), PumpkinMMO.getInstance());
    }
}
