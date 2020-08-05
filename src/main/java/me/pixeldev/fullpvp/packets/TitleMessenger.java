package me.pixeldev.fullpvp.packets;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

import org.bukkit.entity.Player;

import team.unnamed.inject.Inject;
import team.unnamed.inject.process.annotation.Singleton;

@Singleton
public class TitleMessenger {

    @Inject
    private NMSUtils nmsUtils;

    public void sendTitle(Player player, String message, int enter, int in, int out) {
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"),
                enter, in, out
        );

        nmsUtils.sendPacket(player, packetPlayOutTitle);
    }

    public void sendSubtitle(Player player, String message, int enter, int in, int out) {
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"),
                enter, in, out
        );

        nmsUtils.sendPacket(player, packetPlayOutTitle);
    }

    public void sendFull(Player player, String title, String subtitle, int enter, int in, int out) {
        sendTitle(player, title, enter, in, out);
        sendSubtitle(player, subtitle, enter, in, out);
    }

}