package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanMemberJoinEvent;
import me.pixeldev.fullpvp.user.SimpleUser;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.InjectAll;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

@InjectAll
public class PlayerJoinListener implements Listener {

    private Storage<UUID, User> userStorage;
    private Storage<UUID, UserViewer> userViewerStorage;
    private Storage<String, Clan> clanStorage;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = userStorage.findFromData(player.getUniqueId()).orElseGet(SimpleUser::new);

        userStorage.add(player.getUniqueId(), user);

        userViewerStorage.findFromData(player.getUniqueId()).ifPresent(userViewer -> userViewerStorage.add(player.getUniqueId(), userViewer));

        user.getClanName().flatMap(clanName -> clanStorage.find(clanName)).ifPresent(
                clan -> Bukkit.getPluginManager().callEvent(new ClanMemberJoinEvent(player, clan))
        );
    }

}