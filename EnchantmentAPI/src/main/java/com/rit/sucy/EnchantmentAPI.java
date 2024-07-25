//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy;

import com.rit.sucy.commands.Commander;
import com.rit.sucy.enchanting.EEquip;
import com.rit.sucy.enchanting.EListener;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ERomanNumeral;
import com.rit.sucy.service.IModule;

import java.util.*;
import java.util.stream.Collectors;

import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinenchants.EnchantItem;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentAPI extends JavaPlugin {
    private static Hashtable<String, CustomEnchantment> enchantments = new Hashtable();

    private final Map<Class<? extends IModule>, IModule> modules = new HashMap();
    private static String TAG = "[EnchantAPI]";
    private static Random random = new Random();

    public EnchantmentAPI() {
    }

    public void onEnable() {
        this.getCommand("enchantapi").setExecutor(new Commander(this));
        this.reload();
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        enchantments.clear();
        EEquip.clear();
    }

    public void reload() {
        HandlerList.unregisterAll(this);
        EEquip.clear();
        enchantments.clear();
        Plugin[] var1 = this.getServer().getPluginManager().getPlugins();
        int var2 = var1.length;

        int var3;
        for(var3 = 0; var3 < var2; ++var3) {
            Plugin plugin = var1[var3];
            if (plugin instanceof EnchantPlugin) {
                try {
                    ((EnchantPlugin)plugin).registerEnchantments();
                    this.getLogger().info("Loaded enchantments from plugin: " + plugin.getName());
                } catch (Exception var6) {
                    this.getLogger().info("Failed to load enchantments from " + plugin.getName() + ": perhaps it is outdated?");
                    var6.printStackTrace();
                }
            }
        }

        Player[] var7 = Version.getOnlinePlayers();
        var2 = var7.length;

        for(var3 = 0; var3 < var2; ++var3) {
            Player player = var7[var3];
            EEquip.loadPlayer(player);
        }


        new EListener(this);

    }


    public static boolean isRegistered(String enchantmentName) {
        return enchantments.containsKey(enchantmentName.toUpperCase());
    }

    public static CustomEnchantment getEnchantment(String name) {
        return (CustomEnchantment)enchantments.get(name.toUpperCase());
    }

    public static Set<String> getEnchantmentNames() {
        return enchantments.keySet();
    }

    public static Collection<CustomEnchantment> getEnchantments() {
        return enchantments.values();
    }

    public static boolean registerCustomEnchantment(CustomEnchantment enchantment) {
        if (enchantments.containsKey(enchantment.name().toUpperCase())) {
            return false;
        } else {
            enchantments.put(enchantment.name().toUpperCase(), enchantment);
            return true;
        }
    }

    public static void registerCustomEnchantments(CustomEnchantment... enchantments) {
        CustomEnchantment[] var1 = enchantments;
        int var2 = enchantments.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CustomEnchantment enchantment = var1[var3];
            registerCustomEnchantment(enchantment);
        }

    }

    public static boolean unregisterCustomEnchantment(String enchantmentName) {
        if (enchantments.containsKey(enchantmentName.toUpperCase())) {
            enchantments.remove(enchantmentName.toUpperCase());
            return true;
        } else {
            return false;
        }
    }

    public static Map<CustomEnchantment, Integer> getEnchantments(ItemStack item) {
        return (Map)(item != null && item.hasItemMeta() ? getEnchantments(item.getItemMeta()) : new HashMap());
    }

    public static Map<CustomEnchantment, Integer> getEnchantments(ItemMeta meta) {
        Map<CustomEnchantment, Integer> list = new HashMap();
        if (meta == null) {
            return list;
        } else if (!meta.hasLore()) {
            return list;
        } else {
            Iterator var2 = meta.getLore().iterator();

            while(var2.hasNext()) {
                String lore = (String)var2.next();
                String name = ENameParser.parseName(lore);
                int level = ENameParser.parseLevel(lore);
                if (name != null && level != 0 && isRegistered(name)) {
                    CustomEnchantment enchant = getEnchantment(name);
                    if (enchant.canStack() && list.containsKey(enchant)) {
                        level += (Integer)list.get(enchant);
                    }

                    list.put(enchant, level);
                }
            }

            return list;
        }
    }

    public static Map<CustomEnchantment, Integer> getAllEnchantments(ItemStack item) {
        Map<CustomEnchantment, Integer> map = getEnchantments(item);
        if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            Iterator var2 = item.getEnchantments().entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<Enchantment, Integer> entry = (Map.Entry)var2.next();
                map.put(getEnchantment(((Enchantment)entry.getKey()).getName()), entry.getValue());
            }
        }

        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
            Iterator var6 = meta.getStoredEnchants().entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry<Enchantment, Integer> entry = (Map.Entry)var6.next();
                map.put(getEnchantment(((Enchantment)entry.getKey()).getName()), entry.getValue());
            }
        }

        return map;
    }

    public static boolean itemHasEnchantment(ItemStack item, String enchantmentName) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        } else if (!meta.hasLore()) {
            return false;
        } else {
            Iterator var3 = meta.getLore().iterator();

            String lore;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                lore = (String)var3.next();
            } while(!lore.contains(enchantmentName) || ENameParser.parseLevel(lore) <= 0);

            return true;
        }
    }

    public static ItemStack removeEnchantments(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        } else if (!meta.hasLore()) {
            return item;
        } else {
            List<String> lore = meta.getLore();
            Iterator var3 = getEnchantments(item).entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var3.next();
                lore.remove(EnchantType.getEnchantmentTypeFromName(((CustomEnchantment)entry.getKey()).name()).getRarity().getColor() + ((CustomEnchantment)entry.getKey()).name() + " " + ERomanNumeral.numeralOf((Integer)entry.getValue()));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }
    public static ItemStack getEnchantItem(String name){
        CustomEnchantment ench = EnchantType.getEnchantmentTypeFromName(name).getEnchant();
        if(ench == null){
            return null;
        }
        return EnchantItem.getEnchantItem(ench, random.nextInt(ench.max) + 1);
    }
    public static ItemStack getEnchantItem(ItemRarity rarity){
        //line below grabs all enchants of this rarity in a list
        List<EnchantType> possibleEnchants = Arrays.stream(EnchantType.values()).filter(enchantType -> enchantType.getRarity() == rarity).collect(Collectors.toList());
        if (possibleEnchants.isEmpty()) return null;

        String selectedEnchantName = possibleEnchants.get(random.nextInt(possibleEnchants.size())).getEnchant().enchantName;
        return getEnchantItem(selectedEnchantName);
    }
    public static ItemStack getEnchantItem(){
        List<EnchantType> possibleEnchants = Arrays.asList(EnchantType.values());
        if (possibleEnchants.isEmpty()) return null;

        String selectedEnchantName = possibleEnchants.get(random.nextInt(possibleEnchants.size())).getEnchant().enchantName;
        return getEnchantItem(selectedEnchantName);

    }
    public String getTag() {
        return TAG;
    }

    public static void main(String[] args) {
    }

    <T extends IModule> void registerModule(Class<T> clazz, T module) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        } else if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        } else {
            this.modules.put(clazz, module);
            module.starting();
        }
    }

    public <T extends IModule> T deregisterModuleForClass(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        } else {
            T module = clazz.cast(this.modules.get(clazz));
            if (module != null) {
                module.closing();
            }

            return module;
        }
    }

    public <T extends IModule> T getModuleForClass(Class<T> clazz) {
        return clazz.cast(this.modules.get(clazz));
    }
}
