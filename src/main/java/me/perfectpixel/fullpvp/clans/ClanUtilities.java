package me.perfectpixel.fullpvp.clans;

import me.perfectpixel.fullpvp.Storage;

import me.yushust.inject.Inject;
import me.yushust.inject.process.annotation.Singleton;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
public class ClanUtilities {

    @Inject
    private Storage<String, Clan> clanStorage;

    public void sendMessageToMembers(String clanName, String message) {
        clanStorage.find(clanName).ifPresent(clan -> clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            member.sendMessage(message);
        }));
    }

    public void sendMessageToMembers(String clanName, List<String> message) {
        clanStorage.find(clanName).ifPresent(clan -> clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            message.forEach(member::sendMessage);
        }));
    }

}