package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.viewer.UserViewer;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.event.clan.ClanMemberQuitEvent;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

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
            userStorage.saveObject(player.getUniqueId(), user);

            user.getClanName().flatMap(clanName -> clanStorage.find(clanName)).ifPresent(
                    clan -> Bukkit.getPluginManager().callEvent(new ClanMemberQuitEvent(player, clan))
            );
        });

        userViewerStorage.save(player.getUniqueId());
    }

}