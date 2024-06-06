package me.fakepumpkin7.pumpkinmmo.skills;

import lombok.Getter;
import lombok.Setter;
import me.fakepumpkin7.pumpkinmmo.PumpkinMMO;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public abstract class Skill {

    //TODO rewrite save and load to be more compact

    @Getter @Setter
    String name;
    int maxLevel;


    @Getter
    HashMap<UUID, Double> expMap;


    public Skill(String name, int maxLevel){
        this.name = name;
        this.maxLevel = maxLevel;

        load();
    }

    public void load(){
        expMap = new HashMap<>();
        ConfigurationSection section = PumpkinMMO.getInstance().getConfig().getConfigurationSection(name);
        if(section == null){
            section = PumpkinMMO.getInstance().getConfig().createSection(name);
        }
        for(String idStr : section.getKeys(false)){
            UUID id = UUID.fromString(idStr);
            expMap.put(id,section.getDouble(idStr));
        }
    }

    public void save(){
        PumpkinMMO.getInstance().getConfig().createSection(name);
        for(UUID id : expMap.keySet()){
            PumpkinMMO.getInstance().getConfig().getConfigurationSection(name).set(id.toString(), expMap.get(id));
        }
    }



    //returns level,expToNextLevel,expForNextLevel
    //so say level 0 with 30/100 exp
    //returns 0,30,100
    public List<Object> calculateLevel(double exp){
        int level = 0;
        double tempExp = 0;
        int factor = 100;
        while(exp > 0) {
            tempExp = exp;
            if(level <  maxLevel*2/5) {
                factor = level*100;
            } else if(level < maxLevel*4/5){
                factor = level*1000;
            } else if(level <  maxLevel){
                factor = level*10000;
            }

            exp = exp - factor;
            level++;



        }

        return Arrays.asList(level,tempExp,factor);
    }
}
