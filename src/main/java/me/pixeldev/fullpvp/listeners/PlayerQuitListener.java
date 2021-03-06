package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanMemberQuitEvent;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.InjectAll;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;

@InjectAll
public class PlayerQuitListener implements Listener {

    private Cache<UUID, Clan> editMessagesCache;
    private Storage<UUID, User> userStorage;
    private Storage<UUID, UserViewer> userViewerStorage;
    private Storage<String, Clan> clanStorage;
    private Storage<UUID, BackpackUser> backpackUserStorage;

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

        backpackUserStorage.save(player.getUniqueId());
        editMessagesCache.remove(player.getUniqueId());
        userViewerStorage.save(player.getUniqueId());
    }

}