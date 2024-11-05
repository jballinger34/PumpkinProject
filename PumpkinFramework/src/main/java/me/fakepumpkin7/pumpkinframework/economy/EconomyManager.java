package me.fakepumpkin7.pumpkinframework.economy;


public class EconomyManager {

    private static EconomyAPI economyAPI;

    public static void setEconomyAPI(EconomyAPI api) {
        economyAPI = api;
    }

    public static EconomyAPI getEconomyAPI() {
        if (economyAPI == null) {
            throw new IllegalStateException("EnchantAPI implementation not set!");
        }
        return economyAPI;
    }

}
