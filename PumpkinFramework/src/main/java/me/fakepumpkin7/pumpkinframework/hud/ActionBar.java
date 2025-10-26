package me.fakepumpkin7.pumpkinframework.hud;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;


public class ActionBar {

    public static void sendActionBar(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        p.getHandle().playerConnection.sendPacket(ppoc);
    }


    public static void sendPercentageActionBar(Player player, String caption, long progress, long total){
        sendPercentageActionBar(player, caption,progress,total, ChatColor.GREEN, ChatColor.WHITE);
    }

    public static void sendPercentageActionBar(Player player, String caption, long progress, long total, ChatColor progressColour, ChatColor baseColour){
        String message = caption + " " + progressColour;

        int progressPercent = (int) Math.min(100, 100*progress/total);
        int remainingPercent = 100 - progressPercent;

        String progressString = String.join("", Collections.nCopies(progressPercent, "|"));

        String remainingString = String.join("", Collections.nCopies(remainingPercent, "|"));

        message = message + progressString + baseColour + remainingString;

        sendActionBar(player, message);
    }

    public static void reset(Player p) {
        sendActionBar(p, "");
    }

    public static void clear(Player p) {
        sendActionBar(p, "");
    }

}
