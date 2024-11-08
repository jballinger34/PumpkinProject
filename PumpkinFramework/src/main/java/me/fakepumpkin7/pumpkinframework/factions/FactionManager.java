package me.fakepumpkin7.pumpkinframework.factions;

import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;

public class FactionManager {

    private static FactionAPI factionAPI;


    public static void setFactionAPI(FactionAPI api) {
        factionAPI = api;
    }

    public static FactionAPI getFactionAPI() {
        if (factionAPI == null) {
            throw new IllegalStateException("EnchantAPI implementation not set!");
        }
        return factionAPI;
    }



}
