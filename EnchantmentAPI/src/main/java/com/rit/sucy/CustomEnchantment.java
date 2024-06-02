//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy;

import com.rit.sucy.util.PostDefenceEffectRunnable;
import com.rit.sucy.util.PostToolEffectRunnable;
import com.rit.sucy.util.TreeMultiMap;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.ERomanNumeral;
import com.rit.sucy.service.MaterialClass;
import com.rit.sucy.service.MaterialsParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.fakepumpkin7.pumpkinenchants.EnchantType;
import me.fakepumpkin7.pumpkinframework.event.combat.AbstractCustomDamageEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomEnchantment implements Comparable<CustomEnchantment> {
    public static final String DEFAULT_GROUP = "Default";
    protected List<String> suffixGroups;
    protected final String enchantName;
    protected String description;
    protected Material[] naturalItems;
    protected Map<MaterialClass, Integer> weight;
    protected boolean isEnabled;
    protected boolean isTableEnabled;
    protected String group;
    protected double interval;
    protected double base;
    protected int max;
    protected boolean stacks;

    public CustomEnchantment(String name) {
        this(name, (String)null, new Material[0], "Default", 5);
    }

    /** @deprecated */
    public CustomEnchantment(String name, String[] naturalItems) {
        this(name, (String)null, MaterialsParser.toMaterial(naturalItems), "Default", 5);
    }

    public CustomEnchantment(String name, Material[] naturalItems) {
        this(name, (String)null, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description) {
        this(name, description, new Material[0], "Default", 5);
    }

    /** @deprecated */
    public CustomEnchantment(String name, String[] naturalItems, int weight) {
        this(name, (String)null, MaterialsParser.toMaterial(naturalItems), "Default", weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, int weight) {
        this(name, (String)null, naturalItems, "Default", weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, String group) {
        this(name, (String)null, naturalItems, group, 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems) {
        this(name, description, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description, String group) {
        this(name, description, new Material[0], group, 5);
    }

    public CustomEnchantment(String name, String description, int weight) {
        this(name, description, new Material[0], "Default", 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems, String group) {
        this(name, description, naturalItems, group, 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems, int weight) {
        this(name, description, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description, String group, int weight) {
        this(name, description, new Material[0], group, weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, String group, int weight) {
        this(name, (String)null, naturalItems, group, weight);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems, String group, int weight) {
        this.suffixGroups = new ArrayList();
        Validate.notEmpty(name, "Your Enchantment needs a name!");
        Validate.notNull(naturalItems, "Input an empty array instead of \"null\"!");
        Validate.isTrue(weight >= 0, "Weight can't be negative!");
        this.enchantName = name;
        this.description = description;
        this.naturalItems = naturalItems;
        this.isEnabled = true;
        this.group = group;
        this.max = 1;
        this.base = 1.0;
        this.interval = 10.0;
        this.isTableEnabled = true;
        this.stacks = false;
        this.weight = new HashMap();
        this.weight.put(MaterialClass.DEFAULT, weight);
    }

    public String name() {
        return this.enchantName;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canStack() {
        return this.stacks;
    }

    public void setCanStack(boolean stack) {
        this.stacks = stack;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setTableEnabled(boolean value) {
        this.isTableEnabled = value;
    }

    public boolean isTableEnabled() {
        return this.isTableEnabled && ((RootConfig)((EnchantmentAPI)Bukkit.getPluginManager().getPlugin("EnchantmentAPI")).getModuleForClass(RootConfig.class)).getBoolean(RootNode.CUSTOM_TABLE);
    }

    public int getEnchantLevel(int expLevel) {
        for(int i = this.max; i >= 1; --i) {
            if ((double)expLevel >= this.base + this.interval * (double)(i - 1)) {
                return i;
            }
        }

        return 0;
    }

    public int getMaxLevel() {
        return this.max;
    }

    public void setMaxLevel(int value) {
        this.max = value;
    }

    public double getBase() {
        return this.base;
    }

    public void setBase(double value) {
        this.base = value;
    }

    public double getInterval() {
        return this.interval;
    }

    public void setInterval(double value) {
        this.interval = value;
    }

    public List<String> getSuffixGroups() {
        return this.suffixGroups;
    }

    public int getCostPerLevel(boolean withBook) {
        int costIndex = (Integer)this.weight.get(MaterialClass.DEFAULT) * this.max;
        int divisor = withBook ? 2 : 1;
        return (Integer)this.weight.get(MaterialClass.DEFAULT) == 1 ? 8 / divisor : (costIndex < 10 ? 4 / divisor : (costIndex < 30 ? 2 / divisor : 1));
    }

    public void setNaturalMaterials(Material[] materials) {
        this.naturalItems = materials;
    }

    /** @deprecated */
    public String[] getNaturalItems() {
        String[] natItems = new String[this.naturalItems.length];

        for(int i = 0; i < this.naturalItems.length; ++i) {
            natItems[i] = this.naturalItems[i].name();
        }

        return natItems;
    }

    public Material[] getNaturalMaterials() {
        return this.naturalItems;
    }

    public void setWeight(int weight) {
        this.weight.put(MaterialClass.DEFAULT, weight);
    }

    public int getWeight() {
        return (Integer)this.weight.get(MaterialClass.DEFAULT);
    }

    public int getWeight(MaterialClass material) {
        return this.weight.containsKey(material) ? (Integer)this.weight.get(material) : (Integer)this.weight.get(MaterialClass.DEFAULT);
    }

    public boolean canEnchantOnto(ItemStack item) {
        if (this.naturalItems != null && item != null) {
            Material[] var2 = this.naturalItems;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Material validItem = var2[var4];
                if (item.getType() == validItem) {
                    return true;
                }
            }

            return item.getType() == Material.BOOK || item.getType() == Material.ENCHANTED_BOOK;
        } else {
            return false;
        }
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean conflictsWith(CustomEnchantment enchantment) {
        Validate.notNull(enchantment);
        return !this.group.equals("Default") && this.group.equalsIgnoreCase(enchantment.group);
    }

    public boolean conflictsWith(List<CustomEnchantment> enchantmentsToCheck) {
        Validate.notNull(enchantmentsToCheck);
        Iterator var2 = enchantmentsToCheck.iterator();

        CustomEnchantment enchantment;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            enchantment = (CustomEnchantment)var2.next();
        } while(!this.conflictsWith(enchantment));

        return true;
    }

    public boolean conflictsWith(CustomEnchantment... enchantmentsToCheck) {
        Validate.notNull(enchantmentsToCheck);
        CustomEnchantment[] var2 = enchantmentsToCheck;
        int var3 = enchantmentsToCheck.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            CustomEnchantment enchantment = var2[var4];
            if (this.conflictsWith(enchantment)) {
                return true;
            }
        }

        return false;
    }

    public ItemStack addToItem(ItemStack item, int enchantLevel) {
        Validate.notNull(item);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getServer().getItemFactory().getItemMeta(item.getType());
        }

        this.addToItem(item.getType(), meta, enchantLevel);
        item.setItemMeta(meta);
        return item;
    }

    public ItemMeta addToItem(Material material, ItemMeta meta, int enchantLevel) {
        Validate.notNull(meta);
        List<String> metaLore = meta.getLore() == null ? new ArrayList() : meta.getLore();
        String thisName = this.name();
        ChatColor enchantmentColour = EnchantType.getEnchantmentTypeFromName(this.enchantName).getRarity().getColor();
        Iterator var7 = EnchantmentAPI.getEnchantments(meta).entrySet().iterator();

        while(var7.hasNext()) {
            Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var7.next();
            if (((CustomEnchantment)entry.getKey()).name().equals(thisName)) {
                if ((Integer)entry.getValue() >= enchantLevel) {
                    return meta;
                }

                ((List)metaLore).remove(enchantmentColour + thisName + " " + ERomanNumeral.numeralOf((Integer)entry.getValue()));
            }
        }

        ((List)metaLore).add(0, enchantmentColour + this.enchantName + " " + ERomanNumeral.numeralOf(enchantLevel));
        meta.setLore((List)metaLore);
        if (!meta.hasDisplayName() || meta.getDisplayName() == null) {
            meta.setDisplayName(ENameParser.getName(material));
        }

        return meta;
    }

    public ItemStack removeFromItem(ItemStack item) {
        if (!item.hasItemMeta()) {
            return item;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (!meta.hasLore()) {
                return item;
            } else {
                List<String> metaLore = meta.getLore();
                boolean containedEnchantment = false;
                Iterator var5 = EnchantmentAPI.getEnchantments(item).entrySet().iterator();

                while(var5.hasNext()) {
                    Map.Entry<CustomEnchantment, Integer> entry = (Map.Entry)var5.next();
                    if (((CustomEnchantment)entry.getKey()).name().equals(this.name())) {
                        metaLore.remove("§7" + this.name() + " " + ERomanNumeral.numeralOf((Integer)entry.getValue()));
                        containedEnchantment = true;
                    }
                }

                if (containedEnchantment) {
                    meta.setLore(metaLore);
                    item.setItemMeta(meta);
                }

                return item;
            }
        }
    }

    public boolean equals(Object obj) {
        return obj instanceof CustomEnchantment && this.name().equalsIgnoreCase(((CustomEnchantment)obj).name());
    }

    public int compareTo(CustomEnchantment customEnchantment) {
        return this.name().compareTo(customEnchantment.name());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, AbstractCustomDamageEvent event) {
    }

    public void applyDefenseEffect(LivingEntity user, LivingEntity attacker, int enchantLevel, AbstractCustomDamageEvent event, TreeMultiMap<PostDefenceEffectRunnable> postRunTasks) {
    }

    public boolean applyToolEffect(Player player, Block block, List<ItemStack> drops, TreeMultiMap<PostToolEffectRunnable> postRunTasks, int enchantLevel, BlockEvent event) {
        return false;
    }

    public void applyMiscEffect(Player player, int enchantLevel, PlayerInteractEvent event) {
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
    }

    public void applyEntityEffect(Player player, int enchantLevel, PlayerInteractEntityEvent event) {
    }

    public void applyProjectileEffect(LivingEntity user, int enchantLevel, ProjectileLaunchEvent event) {
    }
}
