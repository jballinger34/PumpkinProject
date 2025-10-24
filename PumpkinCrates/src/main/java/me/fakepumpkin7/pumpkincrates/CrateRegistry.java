package me.fakepumpkin7.pumpkincrates;

import me.fakepumpkin7.pumpkincrates.crates.Crate;
import me.fakepumpkin7.pumpkincrates.crates.impl.*;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;

import java.util.HashMap;
import java.util.Map;

public enum CrateRegistry {
    COMMON(new CommonCrate()),
    UNCOMMON(new UncommonCrate()),
    RARE(new RareCrate()),
    MYTHIC(new MythicCrate()),
    HEROIC(new HeroicCrate()),
    ;


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
    public static CrateRegistry getCrateByRarity(ItemRarity rarity){
        for(CrateRegistry crateRegistry : values()){
            if(crateRegistry.getCrate().getRarity() == rarity){
                return crateRegistry;
            }
        }
        return null;
    }

    public Crate getCrate() {
        return crate;
    }
}
