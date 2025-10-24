package me.fakepumpkin7.pumpkinframework.lootcrates;


public class LootCrateManager {
    private static LootCrateAPI lootCrateAPI;

    public static void setLootCrateAPI(LootCrateAPI api) {
        lootCrateAPI = api;
    }

    public static LootCrateAPI getLootCrateAPI() {
        if (lootCrateAPI == null) {
            throw new IllegalStateException("LootCrateAPI implementation not set!");
        }
        return lootCrateAPI;
    }
}
