package me.perfectpixel.fullpvp.clans;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;
import me.yushust.inject.process.annotation.Singleton;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Singleton
public class ClanUtilities {

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Storage<UUID, User> userStorage;

    public void sendMessageToMembers(String clanName, String message) {
        clanStorage.find(clanName).ifPresent(clan -> clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            member.sendMessage(message);
        }));
    }

    public void sendMessageToMembers(String clanName, List<String> message) {
        clanStorage.find(clanName).ifPresent(clan -> clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            message.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

            message.forEach(member::sendMessage);
        }));
    }

    public void sendMessageToMembers(Clan clan, String message) {
        clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            member.sendMessage(message);
        });
    }

    public void sendMessageToMembers(Clan clan, List<String> message) {
        clan.getMembers().forEach(uuid -> {
            Player member = Bukkit.getPlayer(uuid);

            message.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

            message.forEach(member::sendMessage);
        });
    }

    public boolean playerHasClan(Player player) {
        return userStorage.find(player.getUniqueId()).map(user -> user.getClanName().isPresent()).orElse(false);
    }

}