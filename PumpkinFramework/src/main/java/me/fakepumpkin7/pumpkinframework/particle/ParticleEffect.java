package me.fakepumpkin7.pumpkinframework.particle;

import me.fakepumpkin7.pumpkinframework.PumpkinFramework;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public enum ParticleEffect {
    EXPLODE("explode", 0),
    LARGE_EXPLODE("largeexplode", 1),
    HUGE_EXPLOSION("hugeexplosion", 2),
    FIREWORKS_SPARK("fireworksSpark", 3),
    BUBBLE("bubble", 4),
    SPLASH("splash", 5),
    WAKE("wake", 6),
    SUSPEND("suspended", 7),
    DEPTH_SUSPEND("depthsuspend", 8),
    CRIT("crit", 9),
    MAGIC_CRIT("magicCrit", 10),
    SMOKE("smoke", 11),
    LARGE_SMOKE("largesmoke", 12),
    SPELL("spell", 13),
    INSTANT_SPELL("instantSpell", 14),
    MOB_SPELL("mobSpell", 15),
    MOB_SPELL_AMBIENT("mobSpellAmbient", 16),
    WITCH_MAGIC("witchMagic", 17),
    DRIP_WATER("dripWater", 18),
    DRIP_LAVA("dripLava", 19),
    ANGRY_VILLAGER("angryVillager", 20),
    HAPPY_VILLAGER("happyVillager", 21),
    TOWN_AURA("townaura", 22),
    NOTE("note", 23),
    PORTAL("portal", 24),
    ENCHANTMENT_TABLE("enchantmenttable", 25),
    FLAME("flame", 26),
    LAVA("lava", 27),
    FOOTSTEP("footstep", 28),
    CLOUD("cloud", 29),
    RED_DUST("reddust", 30),
    SNOWBALL_POOF("snowballpoof", 31),
    SNOW_SHOVEL("snowshovel", 32),
    SLIME("slime", 33),
    HEART("heart", 34),
    BARRIER("barrier", 35),
    ICONCRACK("iconcrack_", 36),
    BLOCK_CRACK("blockcrack_", 37),
    BLOCK_DUST("blockdust_", 38),
    WATER_DROP("droplet", 39),
    ITEM_TAKE("take", 40),
    MOB_APPEARANCE("mobappearance", 41);

    private final String name;
    private final int id;
    public static CopyOnWriteArrayList<ParticleEffectPacket> particlePacketQueue;
    public static CopyOnWriteArrayList<QueuedWorldEventPacket> sendPacketNearbyQueue;
    private static final Map<String, ParticleEffect> NAME_MAP;
    private static final Map<Integer, ParticleEffect> ID_MAP;

    ParticleEffect(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static void launchFirework(Location l, Color c) {
        // Shoot a firework!
        Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.STAR).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(1);
        fw.setFireworkMeta(fwm);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public static ParticleEffect fromName(String name) {
        if (name == null) {
            return null;
        }
        for (Map.Entry<String, ParticleEffect> e : NAME_MAP.entrySet()) {
            if (!e.getKey().equalsIgnoreCase(name)) continue;
            return e.getValue();
        }
        return null;
    }

    public static ParticleEffect fromId(int id) {
        return ID_MAP.get(id);
    }

    public static void sendWorldEventPacket(PacketPlayOutWorldEvent e, Location l) {
        sendPacketNearbyQueue.add(new QueuedWorldEventPacket(e, l));
    }

    public static void sendToPlayer(ParticleEffect effect, Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        particlePacketQueue.add(new ParticleEffectPacket(player, effect, location, offsetX, offsetY, offsetZ, speed, count));
    }

    public static void sendToLocation(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        particlePacketQueue.add(new ParticleEffectPacket(null, effect, location, offsetX, offsetY, offsetZ, speed, count));
    }

    public static void sendCrackToPlayer(boolean icon, int id, byte data, Player player, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
        Object packet = ParticleEffect.createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
        ParticleEffect.sendPacket(player, packet);
    }

    public static void sendCrackToLocation(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
        Object packet = ParticleEffect.createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.sendPacket(player, packet);
        }
    }

    public static Object createPacket(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        if (count <= 0) {
            count = 1;
        }
        EnumParticle enumParticle = null;
        for (int i = 0; i < EnumParticle.values().length; ++i) {
            if (EnumParticle.values()[i].c() != effect.id) continue;
            enumParticle = EnumParticle.values()[i];
            break;
        }
        Object packet = ParticleEffect.getPacket63WorldParticles();
        ParticleEffect.setValue(packet, "a", enumParticle);
        ParticleEffect.setValue(packet, "b", Float.valueOf((float) location.getX()));
        ParticleEffect.setValue(packet, "c", Float.valueOf((float) location.getY()));
        ParticleEffect.setValue(packet, "d", Float.valueOf((float) location.getZ()));
        ParticleEffect.setValue(packet, "e", Float.valueOf(offsetX));
        ParticleEffect.setValue(packet, "f", Float.valueOf(offsetY));
        ParticleEffect.setValue(packet, "g", Float.valueOf(offsetZ));
        ParticleEffect.setValue(packet, "h", Float.valueOf(speed));
        ParticleEffect.setValue(packet, "i", count);
        ParticleEffect.setValue(packet, "j", false);
        ParticleEffect.setValue(packet, "k", new int[0]);
        return packet;
    }

    public static Object createCrackPacket(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) throws Exception {
        if (count <= 0) {
            count = 1;
        }
        Object packet = ParticleEffect.getPacket63WorldParticles();
        int[] test = new int[]{1, 1};
        String modifier = "iconcrack_" + id;
        if (!icon) {
            test = new int[]{};
            modifier = "tilecrack_" + id + "_" + data;
        }
        EnumParticle enumParticle = null;
        for (int i = 0; i < EnumParticle.values().length; ++i) {
            if (EnumParticle.values()[i].c() != id) continue;
            enumParticle = EnumParticle.values()[i];
            break;
        }
        ParticleEffect.setValue(packet, "a", enumParticle);
        ParticleEffect.setValue(packet, "b", Float.valueOf((float) location.getX()));
        ParticleEffect.setValue(packet, "c", Float.valueOf((float) location.getY()));
        ParticleEffect.setValue(packet, "d", Float.valueOf((float) location.getZ()));
        ParticleEffect.setValue(packet, "e", Float.valueOf(offsetX));
        ParticleEffect.setValue(packet, "f", Float.valueOf(offsetY));
        ParticleEffect.setValue(packet, "g", Float.valueOf(offsetZ));
        ParticleEffect.setValue(packet, "h", Float.valueOf(0.1f));
        ParticleEffect.setValue(packet, "i", count);
        ParticleEffect.setValue(packet, "j", false);
        ParticleEffect.setValue(packet, "k", test);
        return packet;
    }

    public static void playEffect(ParticleEffect pe, Location l, float speed, int amount) {
        particlePacketQueue.add(new ParticleEffectPacket(pe, l, speed, amount));
    }

    private static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    private static Object getEntityPlayer(Player p) throws Exception {
        Method getHandle = p.getClass().getMethod("getHandle");
        return getHandle.invoke(p);
    }

    private static String getPackageName() {
        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private static Object getPacket63WorldParticles() throws Exception {
        Class<?> packet = Class.forName(ParticleEffect.getPackageName() + ".PacketPlayOutWorldParticles");
        for (Constructor<?> cst : packet.getConstructors()) {
            if (cst.getParameterTypes().length != 0) continue;
            return cst.newInstance();
        }
        return packet.getConstructors()[0].newInstance();
    }

    private static void sendPacket(Player p, Object packet) throws Exception {
        Object eplayer = ParticleEffect.getEntityPlayer(p);
        Field playerConnectionField = eplayer.getClass().getField("playerConnection");
        Object playerConnection = playerConnectionField.get(eplayer);
        for (Method m : playerConnection.getClass().getMethods()) {
            if (!m.getName().equalsIgnoreCase("sendPacket")) continue;
            m.invoke(playerConnection, packet);
            return;
        }
    }

    static {
        particlePacketQueue = new CopyOnWriteArrayList();
        sendPacketNearbyQueue = new CopyOnWriteArrayList();
        NAME_MAP = new HashMap<String, ParticleEffect>();
        ID_MAP = new HashMap<Integer, ParticleEffect>();
        for (ParticleEffect effect : ParticleEffect.values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(PumpkinFramework.getInstance(), new Runnable() {
            final double radius = Math.pow(48.0, 2.0);

            @Override
            public void run() {
                for (ParticleEffectPacket pep : particlePacketQueue) {
                    try {
                        Packet packet = pep.getPacket();
                        if (pep.getP() != null) {
                            ParticleEffect.sendPacket(pep.getP(), packet);
                            continue;
                        }
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            ParticleEffect.sendPacket(player, packet);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                particlePacketQueue.clear();
            }
        }, 2L, 2L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(PumpkinFramework.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (QueuedWorldEventPacket data : sendPacketNearbyQueue) {
                    Location l = data.getLocation();
                    PacketPlayOutWorldEvent particles = data.getPacket();
                    ((CraftServer) Bukkit.getServer()).getServer().getPlayerList().sendPacketNearby(l.getBlockX(), l.getBlockY(), l.getBlockZ(), 16.0, ((CraftWorld) l.getWorld()).getHandle().dimension, particles);
                }
                sendPacketNearbyQueue.clear();
            }
        }, 2L, 2L);
    }
}

