package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.event.clan.ClanChatEvent;
import me.perfectpixel.fullpvp.files.FileCreator;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    @Named("config")
    private FileCreator config;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        userStorage.find(player.getUniqueId()).flatMap(User::getClanName).flatMap(clanName -> clanStorage.find(clanName)).ifPresent(clan -> {
            String message = event.getMessage();

            if (event.getMessage().startsWith(config.getString("clans.symbol-chat"))) {
                event.setCancelled(true);

                Bukkit.getPluginManager().callEvent(new ClanChatEvent(player, clan, message.substring(1)));
            }
        });
    }

}