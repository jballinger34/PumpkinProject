package me.fakepumpkin7.pumpkinframework.items;

import org.bukkit.ChatColor;

import java.util.Random;

public enum ItemRarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    SPECIAL(ChatColor.DARK_PURPLE),
    RARE(ChatColor.AQUA),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.YELLOW),
    HEROIC(ChatColor.RED),
    SOUL(ChatColor.DARK_RED);




    ChatColor color;

    ItemRarity(ChatColor color){
        this.color = color;
    }

    public static ItemRarity getRandomChestRarityWeighted(){
        int x = new Random().nextInt(100);
        if (x <= 39) {
            return COMMON;
        }
        if (x <= 49) {
            return UNCOMMON;
        }
        if (x <= 69) {
            return RARE;
        }
        if (x <= 98){
            return MYTHIC;
        }
        else {
            return HEROIC;
        }
    }

    public ChatColor getColor() {
        return color;
    }
}
