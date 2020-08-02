package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanMemberJoinEvent;
import me.pixeldev.fullpvp.user.SimpleUser;
import me.pixeldev.fullpvp.user.User;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Inject
    private Storage<String, Clan> clanStorage;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user;
        Optional<User> userOptional = userStorage.findFromData(player.getUniqueId());

        if (userOptional.isPresent()) {
            user = userOptional.get();

            System.out.println("Si está presente: " + user);
        } else {
            user = new SimpleUser();

            System.out.println("Añadiendo nuevo");
        }

        userStorage.add(player.getUniqueId(), user);

        userViewerStorage.findFromData(player.getUniqueId()).ifPresent(userViewer -> userViewerStorage.add(player.getUniqueId(), userViewer));

        user.getClanName().flatMap(clanName -> clanStorage.find(clanName)).ifPresent(clan ->
                Bukkit.getPluginManager().callEvent(new ClanMemberJoinEvent(player, clan))
        );
    }

}