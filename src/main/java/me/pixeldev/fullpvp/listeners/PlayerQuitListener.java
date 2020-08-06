package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanMemberQuitEvent;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @Inject
    private Cache<UUID, Clan> editMessagesCache;

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Inject
    private Storage<String, Clan> clanStorage;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        userOptional.ifPresent(user -> {
            user.getClanName().flatMap(clanName -> clanStorage.find(clanName)).ifPresent(
                    clan -> Bukkit.getPluginManager().callEvent(new ClanMemberQuitEvent(player, clan))
            );

            userStorage.saveObject(player.getUniqueId(), user);
        });

        editMessagesCache.remove(player.getUniqueId());

        userViewerStorage.save(player.getUniqueId());
    }

}