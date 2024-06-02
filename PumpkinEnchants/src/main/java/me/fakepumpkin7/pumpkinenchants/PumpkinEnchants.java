package me.fakepumpkin7.pumpkinenchants;
import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.EnchantmentAPI;

public final class PumpkinEnchants extends EnchantPlugin {

    private static PumpkinEnchants instance;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Registering Enchants");
        this.registerEnchantments();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @Override
    public void registerEnchantments() {
        for (EnchantType enchantment : EnchantType.values()) {
            EnchantmentAPI.registerCustomEnchantment(enchantment.getEnchant());
        }
    }
    public static PumpkinEnchants getInstance() {
        return instance;
    }
}
