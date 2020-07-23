package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.user.SimpleUser;
import me.perfectpixel.fullpvp.user.User;
import me.yushust.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @Inject
    private Storage<User, UUID> userStorage;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (userStorage.findFromData(player.getUniqueId()).isPresent()) {
            userStorage.add(userStorage.findFromData(player.getUniqueId()).get());
        } else {
            userStorage.add(new SimpleUser(player.getUniqueId()));
        }
    }

}