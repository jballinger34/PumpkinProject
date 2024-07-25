package me.fakepumpkin7.pumpkincrates;

import lombok.Getter;
import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.crates.impl.CommonCrate;

import java.util.HashMap;
import java.util.Map;

public enum CrateRegistry {
    COMMON(new CommonCrate()),
    ;


    @Getter
    private Crate crate;
    CrateRegistry(Crate crate){
        this.crate = crate;
    }


    public static CrateRegistry getCrateById(String id){
        for(CrateRegistry crateRegistry : values()){
            if(crateRegistry.getCrate().getId().equalsIgnoreCase(id)){
                return crateRegistry;
            }
        }
        return null;
    }
}
