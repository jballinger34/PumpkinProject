package me.fakepumpkin7.pumpkinmmo.skills;

import lombok.Getter;
import lombok.Setter;
import me.fakepumpkin7.pumpkinmmo.PumpkinMMO;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.UUID;

public abstract class Skill {

    //TODO
    // rewrite save and load to be more compact

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

}
