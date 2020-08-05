package me.pixeldev.fullpvp.packets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import team.unnamed.inject.process.annotation.Singleton;

@Singleton
public class NMSUtils {

    public CraftPlayer getCraftPlayer(Player player) {
        return (CraftPlayer) player;
    }

    public PlayerConnection getPlayerConnection(Player player) {
        return getCraftPlayer(player).getHandle().playerConnection;
    }

    public void sendPacket(Player player, Packet<?> packet) {
        getPlayerConnection(player).sendPacket(packet);
    }

}