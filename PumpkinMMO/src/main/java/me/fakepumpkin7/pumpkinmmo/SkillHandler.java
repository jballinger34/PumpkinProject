package me.fakepumpkin7.pumpkinmmo;

import lombok.Getter;
import me.fakepumpkin7.pumpkinmmo.skills.combat.CombatSkill;
import me.fakepumpkin7.pumpkinmmo.skills.Skill;

import java.util.ArrayList;

public class SkillHandler {

    @Getter
    ArrayList<Skill> skillList = new ArrayList<>();

    public SkillHandler(){
        skillList.add(new CombatSkill());
    }

    public Skill getSkillFromName(String name){
        for(Skill s : skillList){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }


    public void saveToConfig(){
        for(Skill skill : skillList){
            skill.save();
        }
        PumpkinMMO.getInstance().saveConfig();

    }

}
