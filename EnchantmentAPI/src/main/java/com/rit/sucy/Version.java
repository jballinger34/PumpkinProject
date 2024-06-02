//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rit.sucy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Version {
    private static String pack;

    public Version() {
    }

    private static void init() {
        if (pack == null) {
            Matcher matcher = Pattern.compile("v\\d+_\\d+_R\\d+").matcher(Bukkit.getServer().getClass().getPackage().getName());
            if (matcher.find()) {
                pack = matcher.group();
            } else {
                pack = "._.";
            }
        }

    }

    public static String getPackage() {
        return pack;
    }

    public static Player[] getOnlinePlayers() {
        init();
        if (pack.startsWith("v1_9")) {
            ArrayList<Player> list = new ArrayList();
            Collection<? extends Player> online = Bukkit.getOnlinePlayers();
            Iterator var2 = online.iterator();

            while(var2.hasNext()) {
                Object player = var2.next();
                if (player instanceof Player) {
                    list.add((Player)player);
                }
            }

            return (Player[])list.toArray(new Player[list.size()]);
        } else {
            try {
                return (Player[])((Player[])Bukkit.class.getMethod("getOnlinePlayers").invoke((Object)null));
            } catch (Exception var4) {
                return new Player[0];
            }
        }
    }
}
