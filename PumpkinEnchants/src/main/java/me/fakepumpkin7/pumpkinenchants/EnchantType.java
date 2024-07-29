package me.fakepumpkin7.pumpkinenchants;

import lombok.Getter;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.*;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.boots.Leap;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.boots.Speedy;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.helmet.Clarity;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.leggings.Arsonist;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.Cactus;
import me.fakepumpkin7.pumpkinenchants.enchants.armour.stacks.*;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.*;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.bow.ArrowRain;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.bow.Snare;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.*;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword.Block;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword.Divine;
import me.fakepumpkin7.pumpkinenchants.enchants.weapons.melee.sword.Lifesteal;
import me.fakepumpkin7.pumpkinframework.items.ItemRarity;

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
    ARSONIST(new Arsonist(), ItemRarity.UNCOMMON),
    SPEEDY(new Speedy(), ItemRarity.UNCOMMON),
    FORTIFY(new Fortify(), ItemRarity.RARE),
    CLARITY(new Clarity(), ItemRarity.COMMON),
    OXYGEN(new Clarity(), ItemRarity.COMMON),
    PYROMANIAC(new Pyromaniac(), ItemRarity.UNCOMMON),
    SPECTRAL(new Spectral(), ItemRarity.MYTHIC),
    MARKSMAN(new Marksman(), ItemRarity.MYTHIC),
    ADRENALINE(new Adrenaline(), ItemRarity.RARE),
    DODGE(new Dodge(), ItemRarity.MYTHIC),
    LEAP(new Leap(), ItemRarity.UNCOMMON),
    TRICKSHOT(new Trickshot(), ItemRarity.MYTHIC),
    DUEL_WIELD(new DuelWield(), ItemRarity.MYTHIC),
    SOUL_HARVEST(new SoulHarvest(), ItemRarity.UNCOMMON),
    PICKPOCKET(new Pickpocket(), ItemRarity.RARE),
    FIRE_ASPECT(new FireAspect(), ItemRarity.COMMON),
    HELLISH(new Hellish(), ItemRarity.MYTHIC),
    BLEED(new Bleed(), ItemRarity.UNCOMMON),
    ARROW_RAIN(new ArrowRain(), ItemRarity.RARE),
    SNARE(new Snare(),ItemRarity.RARE),
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
