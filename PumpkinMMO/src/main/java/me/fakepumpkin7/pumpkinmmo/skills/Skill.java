package me.fakepumpkin7.pumpkinmmo.skills;

import lombok.Getter;
import lombok.Setter;
import me.fakepumpkin7.pumpkinmmo.PumpkinMMO;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Platform;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public abstract class Skill {

    @Getter @Setter
    String name;

    int maxLevel;

    HashMap<Player, Double> expMap;


    public Skill(String name, int maxLevel){
        this.name = name;
        this.maxLevel = maxLevel;

        load();
    }

    public void load(){
        expMap = (HashMap<Player, Double>) PumpkinMMO.getInstance().getConfig().get(name);
        if(expMap == null){
            expMap = new HashMap<>();
        }
    }

    public void save(){
        PumpkinMMO.getInstance().getConfig().set(name, expMap);
    }


}
