package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanChatEvent;
import me.pixeldev.fullpvp.event.clan.ClanEditMessagesEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

@InjectAll
public class AsyncPlayerChatListener implements Listener {

    private Cache<UUID, Clan> editMessagesCache;
    private Storage<UUID, User> userStorage;
    private Storage<String, Clan> clanStorage;

    @Named("config")
    private FileCreator config;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        userStorage.find(player.getUniqueId()).flatMap(User::getClanName).flatMap(clanName -> clanStorage.find(clanName)).ifPresent(clan -> {
            if (message.startsWith(config.getString("clans.symbol-chat"))) {
                event.setCancelled(true);

                Bukkit.getPluginManager().callEvent(new ClanChatEvent(player, clan, message.substring(1)));
            }
        });

        editMessagesCache.find(player.getUniqueId()).ifPresent(clan -> {
            event.setCancelled(true);

            Bukkit.getPluginManager().callEvent(
                    new ClanEditMessagesEvent(player, clan, message)
            );
        });
    }

}