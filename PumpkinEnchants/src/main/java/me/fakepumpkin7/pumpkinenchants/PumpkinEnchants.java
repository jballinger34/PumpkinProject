package me.fakepumpkin7.pumpkinenchants;

import com.rit.sucy.EnchantItemManager;
import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.EnchantmentAPI;
import me.fakepumpkin7.pumpkinenchants.cmd.CmdEnchItem;
import me.fakepumpkin7.pumpkinenchants.cmd.cmdenchants.CmdEnchants;
import org.bukkit.Bukkit;

public final class PumpkinEnchants extends EnchantPlugin {

    private static PumpkinEnchants instance;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Registering Enchants");

        EnchantItemManager.setEnchantItem(EnchantItem.getInstance());

        registerEnchantments();
        registerListeners();
        registerCommands();

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
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);

    }
    private void registerCommands(){
        this.getCommand("enchitem").setExecutor(new CmdEnchItem());
        Bukkit.getPluginManager().registerEvents(new CmdEnchants(), this);

    }
    public static PumpkinEnchants getInstance() {
        return instance;
    }
}
