package me.fakepumpkin7.pumpkinmmo;

import me.fakepumpkin7.pumpkinmmo.skills.combat.CombatSkill;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;

import java.util.ArrayList;

public class SkillHandler {

    ArrayList<Skill> skillList = new ArrayList<>();

    public SkillHandler(){
        skillList.add(new CombatSkill());




    }



    public void saveToConfig(){
        for(Skill skill : skillList){
            skill.save();
        }
        PumpkinMMO.getInstance().saveConfig();

    }

}
