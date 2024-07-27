package me.fakepumpkin7.pumpkinenchants;

import lombok.Getter;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.*;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.Block;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.Divine;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.Lifesteal;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.Sharpness;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;
import org.bukkit.entity.Item;

import java.util.HashMap;
import java.util.Map;

public enum EnchantType {

    HEALTHY(new Healthy(), ItemRarity.MYTHIC),
    LIFESTEAL(new Lifesteal(), ItemRarity.MYTHIC),
    SHARPNESS(new Sharpness(), ItemRarity.MYTHIC),
    REFLECT(new Reflect(), ItemRarity.MYTHIC),
    PROTECTION(new Protection(), ItemRarity.MYTHIC),
    LIFE_ESSENCE(new LifeEssence(), ItemRarity.MYTHIC),
    CACTUS(new Cactus(), ItemRarity.UNCOMMON),
    DIVINE(new Divine(), ItemRarity.RARE),
    BLOCK(new Block(), ItemRarity.UNCOMMON),

    ;

    @Getter
    BaseEnchant enchant;
    @Getter
    ItemRarity rarity;

    EnchantType(BaseEnchant enchant, ItemRarity rarity){
        this.enchant = enchant;
        this.rarity = rarity;
        name = enchant.name();
    }

    String name;
    private static final Map<String, EnchantType> nameToEnumMap = new HashMap<>();

    static {
        for (EnchantType enchantmentType : values()) {
            nameToEnumMap.put(enchantmentType.name, enchantmentType);
        }
    }

    public static EnchantType getEnchantmentTypeFromName(String enchantmentName) {
        return nameToEnumMap.getOrDefault(enchantmentName, null);
    }


}
