package me.fakepumpkin7.pumpkinframework.hud;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {

    //fadeIn is ticks to fade in over
    //stay is ticks to stay
    //fadeout is ticks to fade out over
    public static void sendTitle(Player player, String title, TitleColor color, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        String raw = String.format("{\"text\":\"\",\"extra\":[{\"text\":\"%s\",\"color\":\"%s\"}]}", title, color.getName());
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a(raw);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        connection.sendPacket(titlePacket);
    }

    public static void sendSubtitle(Player player, String subtitle, TitleColor color, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        String raw = String.format("{\"text\":\"\",\"extra\":[{\"text\":\"%s\",\"color\":\"%s\"}]}", subtitle, color.getName());
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a(raw);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleJSON, fadeIn, stay, fadeOut);
        connection.sendPacket(titlePacket);
    }

    public enum TitleColor {
        //some of these may not work, just added all the chatcolor enums
        BLACK,
        DARK_BLUE,
        DARK_AQUA,
        DARK_RED,
        DARK_GRAY,
        RED,
        DARK_GREEN,
        GREEN,
        BLUE,
        YELLOW,
        GOLD,
        AQUA,
        PURPLE,
        DARK_PURPLE,
        LIGHT_PURPLE,
        GRAY,
        WHITE;


        public String getName() {
            return this.name().toLowerCase();
        }
    }


}
