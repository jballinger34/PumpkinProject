package me.fakepumpkin7.pumpkinframework.hud;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;

public class ActionBar {

    private Class<?> nmsChatSerializer;
    private Class<?> nmsPacketTitle;
    private Class<?> nmsPacketChat;
    private Class<?> nmsChatBaseComponent;
    private Object nmsIChatBaseComponent;
    private final String handle = "getHandle";
    private final String playerConnection = "playerConnection";
    private final String sendPacket = "sendPacket";

    private void loadClasses() {
        nmsIChatBaseComponent = getNMSClass("IChatBaseComponent");
        nmsPacketChat = getNMSClass("PacketPlayOutChat");

        nmsChatBaseComponent = getNMSClass("IChatBaseComponent");

        if (getVersion().contains("1_8")) {
            if (getVersion().contains("R1")) {
                nmsChatSerializer = getNMSClass("ChatSerializer");
                nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
                getNMSClass("EnumTitleAction");
            } else if (getVersion().contains("R2") || getVersion().contains("R3")) {
                nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
                nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
                getNMSClass("PacketPlayOutTitle$EnumTitleAction");
            }
        } else if (getVersion().contains("1_9") || getVersion().contains("1_10") || getVersion().contains("1_11")) {
            nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
            nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
            getNMSClass("PacketPlayOutTitle$EnumTitleAction");
        }
    }

    public Class<?> getNMSChatSerializer() {
        return nmsChatSerializer;
    }

    public Class<?> getNMSIChatBaseComponent() {
        return nmsIChatBaseComponent.getClass();
    }

    public Class<?> getNMSPacketTitle() {
        return nmsPacketTitle;
    }

    public void sendActionBar(Player player, String message) {
        loadClasses();
        try {
            Object handle = getMethod(player.getClass(), this.handle, new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = getField(handle.getClass(), this.playerConnection).get(handle);
            Object serializedMessage = getMethod(this.nmsChatSerializer, "a", String.class).invoke(this.nmsChatSerializer, "{\"text\":\"" + color(message) + "\"}");
            Object packet = this.nmsPacketChat.getConstructor(this.nmsChatBaseComponent, byte.class).newInstance(serializedMessage, (byte) 2);

            getMethod(playerConnection.getClass(), this.sendPacket).invoke(playerConnection, packet);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void sendActionBarToAllPlayers(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, message);
        }
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    private Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods())
            if (m.getName().equals(name) && (args.length == 0 || classListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        return null;
    }

    private boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }
    private static String color(String message){
        return message != null && !message.isEmpty() ? ChatColor.translateAlternateColorCodes('&', message) : message;
    }
}
