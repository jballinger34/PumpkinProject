package me.fakepumpkin7.pumpkinframework.items;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    SPECIAL(ChatColor.DARK_PURPLE),
    RARE(ChatColor.AQUA),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.YELLOW),
    HEROIC(ChatColor.RED),
    SOUL(ChatColor.DARK_RED);



    @Getter
    ChatColor color;

    ItemRarity(ChatColor color){
        this.color = color;
    }

}
