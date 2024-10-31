package me.fakepumpkin7.pumpkinframework.armor;

import me.fakepumpkin7.pumpkinframework.enchants.EnchantAPI;

public class ArmourSetManager {

    private static ArmourSetsAPI armourSetsAPI;

    public static void setEnchantAPI(ArmourSetsAPI api) {
        armourSetsAPI = api;
    }

    public static ArmourSetsAPI getArmourSetsAPI() {
        if (armourSetsAPI == null) {
            throw new IllegalStateException("EnchantAPI implementation not set!");
        }
        return armourSetsAPI;
    }





}
