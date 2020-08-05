package me.pixeldev.fullpvp.packets;

import team.unnamed.inject.Inject;
import team.unnamed.inject.process.annotation.Singleton;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.entity.Player;

@Singleton
public class ActionbarMessenger {

    @Inject
    private NMSUtils nmsUtils;

    public void sendActionbar(Player player, String message) {
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
        nmsUtils.sendPacket(player, packetPlayOutChat);
    }

}