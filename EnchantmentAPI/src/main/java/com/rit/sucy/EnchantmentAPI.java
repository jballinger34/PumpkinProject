//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy;

import com.rit.sucy.Anvil.AnvilListener;
import com.rit.sucy.commands.Commander;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.enchanting.EEquip;
import com.rit.sucy.enchanting.EListener;
import com.rit.sucy.enchanting.VanillaData;
import com.rit.sucy.enchanting.VanillaEnchantment;
import com.rit.sucy.lore.LoreConfig;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ERomanNumeral;
import com.rit.sucy.service.IModule;
import com.rit.sucy.service.PermissionNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.fakepumpkin7.pumpkinenchants.EnchantType;
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
    private Hashtable<String, List<String>> adjectives;
    private Hashtable<String, List<String>> weapons;
    private Hashtable<String, List<String>> suffixes;
    private final Map<Class<? extends IModule>, IModule> modules = new HashMap();
    private static String TAG = "[EnchantAPI]";

    public EnchantmentAPI() {
    }

    public void onEnable() {
        this.getCommand("enchantapi").setExecutor(new Commander(this));
        this.registerModule(RootConfig.class, new RootConfig(this));
        this.reload();
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        enchantments.clear();
        EEquip.clear();
    }

    public void reload() {
        this.adjectives = (new LoreConfig(this, "adjectives")).getLists();
        this.weapons = (new LoreConfig(this, "weapons")).getLists();
        this.suffixes = (new LoreConfig(this, "suffixes")).getLists();
        HandlerList.unregisterAll(this);
        EEquip.clear();
        enchantments.clear();
        this.loadVanillaEnchantments();
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

        ((RootConfig)this.getModuleForClass(RootConfig.class)).reload();
        new EListener(this);
        if (((RootConfig)this.getModuleForClass(RootConfig.class)).getBoolean(RootNode.ANVIL_ENABLED)) {
            new AnvilListener(this);
        }

    }

    public static List<CustomEnchantment> getAllValidEnchants(ItemStack item) {
        return getAllValidEnchants(item, 45);
    }

    public static List<CustomEnchantment> getAllValidEnchants(ItemStack item, int level) {
        List<CustomEnchantment> validEnchantments = new ArrayList();
        boolean book = item.getType() == Material.BOOK;
        Iterator var4 = getEnchantments().iterator();

        while(true) {
            CustomEnchantment enchantment;
            do {
                if (!var4.hasNext()) {
                    return validEnchantments;
                }

                enchantment = (CustomEnchantment)var4.next();
            } while(!enchantment.canEnchantOnto(item) && !book);

            if (enchantment.isEnabled() && enchantment.getEnchantLevel(level) >= 1 && enchantment.isTableEnabled()) {
                validEnchantments.add(enchantment);
            }
        }
    }

    public static List<CustomEnchantment> getAllValidEnchants(ItemStack item, Permissible enchanter) {
        return getAllValidEnchants(item, enchanter, 45);
    }

    public static List<CustomEnchantment> getAllValidEnchants(ItemStack item, Permissible enchanter, int level) {
        List<CustomEnchantment> validEnchantments = new ArrayList();
        boolean book = item.getType() == Material.BOOK;
        Iterator var5 = getEnchantments().iterator();

        while(true) {
            CustomEnchantment enchantment;
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    return validEnchantments;
                                }

                                enchantment = (CustomEnchantment)var5.next();
                            } while(!enchantment.canEnchantOnto(item) && !book);
                        } while(!enchantment.isEnabled());
                    } while(enchantment.getEnchantLevel(level) < 1);
                } while(!enchantment.isTableEnabled());
            } while(!enchanter.hasPermission(PermissionNode.ENCHANT.getNode()) && (!enchanter.hasPermission(PermissionNode.ENCHANT_VANILLA.getNode()) || !(enchantment instanceof VanillaEnchantment)) && !enchanter.hasPermission(PermissionNode.ENCHANT.getNode() + "." + enchantment.name().replace(" ", "").toLowerCase()));

            validEnchantments.add(enchantment);
        }
    }

    public String getAdjective(int level) {
        if (this.adjectives.containsKey("tier" + level)) {
            List<String> list = (List)this.adjectives.get("tier" + level);
            return (String)list.get((int)(Math.random() * (double)list.size()));
        } else {
            ArrayList<String> all = new ArrayList();
            Iterator var3 = this.adjectives.values().iterator();

            while(var3.hasNext()) {
                List<String> list = (List)var3.next();
                Iterator var5 = list.iterator();

                while(var5.hasNext()) {
                    String adjective = (String)var5.next();
                    all.add(adjective);
                }
            }

            return (String)all.get((int)(Math.random() * (double)all.size()));
        }
    }

    public String getWeapon(String item) {
        String[] pieces = item.split("_");
        String type = pieces[pieces.length - 1].toLowerCase();
        if (this.weapons.containsKey(type)) {
            List<String> list = (List)this.weapons.get(type);
            return (String)list.get((int)(Math.random() * (double)list.size()));
        } else {
            return type.substring(0, 1).toUpperCase() + type.substring(1);
        }
    }

    public String getSuffix(CustomEnchantment enchant) {
        ArrayList<String> options = new ArrayList();
        Iterator var3;
        Iterator var5;
        String suffix;
        if (enchant.getSuffixGroups().isEmpty()) {
            var3 = this.suffixes.values().iterator();

            while(var3.hasNext()) {
                List<String> list = (List)var3.next();
                var5 = list.iterator();

                while(var5.hasNext()) {
                    suffix = (String)var5.next();
                    if (!options.contains(suffix)) {
                        options.add(suffix);
                    }
                }
            }
        } else {
            var3 = enchant.getSuffixGroups().iterator();

            while(var3.hasNext()) {
                String group = (String)var3.next();
                var5 = ((List)this.suffixes.get(group)).iterator();

                while(var5.hasNext()) {
                    suffix = (String)var5.next();
                    if (!options.contains(suffix)) {
                        options.add(suffix);
                    }
                }
            }
        }

        return (String)options.get((int)(Math.random() * (double)options.size()));
    }

    private void loadVanillaEnchantments() {
        VanillaData[] var1 = VanillaData.values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            VanillaData defaults = var1[var3];
            VanillaEnchantment vanilla = new VanillaEnchantment(defaults.getEnchantment(), defaults.getItems(), defaults.getGroup(), defaults.getSuffixGroup(), defaults.getEnchantWeight(), defaults.getBase(), defaults.getInterval(), defaults.getMaxLevel(), defaults.name());
            registerCustomEnchantment(vanilla);
        }

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
        } else if (!enchantment.isEnabled()) {
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
