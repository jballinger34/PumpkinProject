package me.fakepumpkin7.pumpkinframework.particle;


import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Location;


final class QueuedWorldEventPacket {
    PacketPlayOutWorldEvent packet;
    Location location;

    QueuedWorldEventPacket(PacketPlayOutWorldEvent p, Location l) {
        this.packet = p;
        this.location = l;
    }

    public PacketPlayOutWorldEvent getPacket() {
        return this.packet;
    }

    public Location getLocation() {
        return this.location;
    }
}
