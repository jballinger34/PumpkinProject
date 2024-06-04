package me.fakepumpkin7.pumpkinarmour.struct;

import me.fakepumpkin7.pumpkinarmour.impl.*;

public enum ArmourSetRegistry {

    EMERALD_ARMOUR(new EmeraldArmour()),
    MIDAS_ARMOUR(new MidasArmour()),
    ;

    ArmourSet set;

    ArmourSetRegistry(ArmourSet set){
        this.set = set;
    }

    public ArmourSet getSet() {
        return set;
    }

    public static ArmourSet getSetById(String id){
        for(ArmourSetRegistry setEntry : ArmourSetRegistry.values()){
            if(setEntry.getSet().getId().equals(id)){
                return setEntry.getSet();
            }
        }
        return null;
    }



}
