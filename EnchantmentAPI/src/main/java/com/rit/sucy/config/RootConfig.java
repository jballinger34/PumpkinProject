//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy.config;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.enchanting.VanillaEnchantment;
import com.rit.sucy.service.MaterialsParser;
import com.rit.sucy.service.ModularConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RootConfig extends ModularConfig {
    public static String baseNode = "EnchantmentAPI.";
    private String customNode = "Custom Enchantments.";
    private String vanillaNode = "Vanilla Enchantments.";

    public RootConfig(EnchantmentAPI plugin) {
        super(plugin);
    }

    public void starting() {
    }

    public void closing() {
        this.plugin.reloadConfig();
        this.plugin.saveConfig();
    }

    public void save() {
        this.plugin.saveConfig();
    }

    public void set(String path, Object value) {
        ConfigurationSection config = this.plugin.getConfig();
        config.set(path, value);
        this.plugin.saveConfig();
    }

    public void reload() {
        this.plugin.reloadConfig();
        this.loadDefaults(this.plugin.getConfig());
        this.loadSettings(this.plugin.getConfig());
        this.boundsCheck();
        this.loadEnchantments(this.plugin.getConfig());
        this.loadLanguage(this.plugin.getConfig());
        this.writeConfig();
    }

    public void loadSettings(ConfigurationSection config) {
        RootNode[] var2 = RootNode.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            RootNode node = var2[var4];
            this.updateOption(node, config);
        }

    }

    public void loadDefaults(ConfigurationSection config) {
        RootNode[] var2 = RootNode.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            RootNode node = var2[var4];
            if (!config.contains(node.getPath())) {
                config.set(node.getPath(), node.getDefaultValue());
            }
        }

    }

    public void loadLanguage(ConfigurationSection config) {
        LanguageNode[] var2 = LanguageNode.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            LanguageNode node = var2[var4];
            if (config.contains(node.getFullPath()) && config.getStringList(node.getFullPath()).size() != 0) {
                if ((node == LanguageNode.TABLE_ENCHANTABLE || node == LanguageNode.TABLE_UNENCHANTABLE) && config.getStringList(node.getFullPath()).size() != 2) {
                    config.set(node.getFullPath(), node.getDefaultValue());
                }
            } else {
                config.set(node.getFullPath(), node.getDefaultValue());
            }
        }

    }

    public void loadEnchantments(ConfigurationSection config) {
        Collection<CustomEnchantment> enchantments = EnchantmentAPI.getEnchantments();
        Iterator var3 = enchantments.iterator();

        while(var3.hasNext()) {
            CustomEnchantment enchantment = (CustomEnchantment)var3.next();
            String section = enchantment instanceof VanillaEnchantment ? this.vanillaNode : this.customNode;
            EnchantmentNode[] var6 = EnchantmentNode.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                EnchantmentNode node = var6[var8];
                String path = baseNode + section + enchantment.name() + node.getPath();
                if (config.contains(path)) {
                    Object obj = config.get(path);
                    switch (node) {
                        case ENABLED:
                            if (obj instanceof Boolean) {
                                enchantment.setEnabled((Boolean)obj);
                            }
                            break;
                        case TABLE:
                            if (obj instanceof Boolean) {
                                enchantment.setTableEnabled((Boolean)obj);
                            }
                            break;
                        case ITEMS:
                            if (obj instanceof List) {
                                List<String> stringList = (List)obj;
                                Material[] materials = MaterialsParser.toMaterial((String[])stringList.toArray(new String[stringList.size()]));
                                enchantment.setNaturalMaterials(materials);
                            }
                            break;
                        case WEIGHT:
                            if (obj instanceof Integer) {
                                enchantment.setWeight((Integer)obj);
                            }
                            break;
                        case GROUP:
                            if (obj instanceof String) {
                                enchantment.setGroup((String)obj);
                            }
                            break;
                        case MAX:
                            if (obj instanceof Integer) {
                                enchantment.setMaxLevel((Integer)obj);
                            }
                            break;
                        case BASE:
                            if (obj instanceof Integer) {
                                enchantment.setBase((double)(Integer)obj);
                            } else if (obj instanceof Double) {
                                enchantment.setBase((Double)obj);
                            }
                            break;
                        case INTERVAL:
                            if (obj instanceof Double) {
                                enchantment.setInterval((Double)obj);
                            } else if (obj instanceof Integer) {
                                enchantment.setInterval((double)(Integer)obj);
                            }
                            break;
                        case STACK:
                            if (obj instanceof Boolean) {
                                enchantment.setCanStack((Boolean)obj);
                            }
                            break;
                        default:
                            throw new UnsupportedOperationException("The node " + node.name() + " hasn't been configured yet");
                    }
                }
            }
        }

    }

    public void writeConfig() {
        FileConfiguration config = this.plugin.getConfig();
        YamlConfiguration out = new YamlConfiguration();
        if (RootNode.values().length > 0) {
            RootNode[] var3 = RootNode.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                RootNode node = var3[var5];
                out.set(node.getPath(), config.get(node.getPath()));
            }
        }

        List<CustomEnchantment> customEnchantments = new ArrayList(EnchantmentAPI.getEnchantments());
        List<CustomEnchantment> vanillaEnchantments = new ArrayList();
        Iterator<CustomEnchantment> iter = customEnchantments.iterator();

        while(iter.hasNext()) {
            CustomEnchantment customEnchant = (CustomEnchantment)iter.next();
            if (customEnchant instanceof VanillaEnchantment) {
                vanillaEnchantments.add(customEnchant);
                iter.remove();
            }
        }

        Collections.sort(customEnchantments);
        Iterator var16 = customEnchantments.iterator();

        CustomEnchantment enchant;
        String path;
        while(var16.hasNext()) {
            enchant = (CustomEnchantment)var16.next();
            path = baseNode + this.customNode + enchant.name();
            if (this.getBoolean(RootNode.CUSTOM_ENABLED)) {
                out.set(path + EnchantmentNode.ENABLED.getPath(), enchant.isEnabled());
            }

            if (this.getBoolean(RootNode.CUSTOM_TABLE)) {
                out.set(path + EnchantmentNode.TABLE.getPath(), enchant.isTableEnabled());
            }

            if (this.getBoolean(RootNode.CUSTOM_WEIGHT)) {
                out.set(path + EnchantmentNode.WEIGHT.getPath(), enchant.getWeight());
            }

            if (this.getBoolean(RootNode.CUSTOM_GROUPS)) {
                out.set(path + EnchantmentNode.GROUP.getPath(), enchant.getGroup());
            }

            if (this.getBoolean(RootNode.CUSTOM_MAX)) {
                out.set(path + EnchantmentNode.MAX.getPath(), enchant.getMaxLevel());
            }

            if (this.getBoolean(RootNode.CUSTOM_BASE)) {
                out.set(path + EnchantmentNode.BASE.getPath(), enchant.getBase());
            }

            if (this.getBoolean(RootNode.CUSTOM_INTERVAL)) {
                out.set(path + EnchantmentNode.INTERVAL.getPath(), enchant.getInterval());
            }

            if (this.getBoolean(RootNode.CUSTOM_ITEMS)) {
                out.set(path + EnchantmentNode.ITEMS.getPath(), MaterialsParser.toStringArray(enchant.getNaturalMaterials()));
            }

            if (this.getBoolean(RootNode.CUSTOM_STACK)) {
                out.set(path + EnchantmentNode.STACK.getPath(), enchant.canStack());
            }
        }

        Collections.sort(vanillaEnchantments);
        var16 = vanillaEnchantments.iterator();

        while(var16.hasNext()) {
            enchant = (CustomEnchantment)var16.next();
            path = baseNode + this.vanillaNode + enchant.name();
            if (this.getBoolean(RootNode.VANILLA_ENABLED)) {
                out.set(path + EnchantmentNode.ENABLED.getPath(), enchant.isEnabled());
            }

            if (this.getBoolean(RootNode.VANILLA_TABLE)) {
                out.set(path + EnchantmentNode.TABLE.getPath(), enchant.isTableEnabled());
            }

            if (this.getBoolean(RootNode.VANILLA_WEIGHT)) {
                out.set(path + EnchantmentNode.WEIGHT.getPath(), enchant.getWeight());
            }

            if (this.getBoolean(RootNode.VANILLA_GROUPS)) {
                out.set(path + EnchantmentNode.GROUP.getPath(), enchant.getGroup());
            }

            if (this.getBoolean(RootNode.VANILLA_MAX)) {
                out.set(path + EnchantmentNode.MAX.getPath(), enchant.getMaxLevel());
            }

            if (this.getBoolean(RootNode.VANILLA_BASE)) {
                out.set(path + EnchantmentNode.BASE.getPath(), enchant.getBase());
            }

            if (this.getBoolean(RootNode.VANILLA_INTERVAL)) {
                out.set(path + EnchantmentNode.INTERVAL.getPath(), enchant.getInterval());
            }

            if (this.getBoolean(RootNode.VANILLA_ITEMS)) {
                out.set(path + EnchantmentNode.ITEMS.getPath(), MaterialsParser.toStringArray(enchant.getNaturalMaterials()));
            }
        }

        LanguageNode[] var17 = LanguageNode.values();
        int var18 = var17.length;

        for(int var20 = 0; var20 < var18; ++var20) {
            LanguageNode node = var17[var20];
            String path2 = node.getFullPath();
            out.set(path2, config.get(path2));
        }

        try {
            String string = this.plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml";
            out.save(string);
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    public void boundsCheck() {
    }
}
