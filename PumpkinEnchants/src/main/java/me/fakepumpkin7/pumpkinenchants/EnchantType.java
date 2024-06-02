package me.fakepumpkin7.pumpkinenchants;

import lombok.Getter;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.Healthy;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;

import java.util.HashMap;
import java.util.Map;

public enum EnchantType {

    HEALTHY(new Healthy(), ItemRarity.MYTHIC),


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
