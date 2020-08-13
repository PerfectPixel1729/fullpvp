package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.Backpack;
import me.pixeldev.fullpvp.backpack.SimpleBackpack;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;
import me.pixeldev.fullpvp.backpack.user.SimpleBackpackUser;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.event.clan.ClanMemberJoinEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.user.SimpleUser;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.InjectAll;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;
import java.util.UUID;

@InjectAll
public class PlayerJoinListener implements Listener {

    private Plugin plugin;
    private Storage<UUID, User> userStorage;
    private Storage<UUID, UserViewer> userViewerStorage;
    private Storage<String, Clan> clanStorage;
    private Storage<UUID, BackpackUser> backpackUserStorage;
    private Cache<UUID, FileCreator> backpackFileCache;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = userStorage.findFromData(player.getUniqueId()).orElseGet(SimpleUser::new);

        userStorage.add(player.getUniqueId(), user);

        backpackFileCache.add(player.getUniqueId(), new FileCreator(plugin, player.getUniqueId().toString(), ".yml", "backpacks"));

        Optional<BackpackUser> backpackUserOptional = backpackUserStorage.findFromData(player.getUniqueId());

        if (backpackUserOptional.isPresent()) {
            backpackUserStorage.add(player.getUniqueId(), backpackUserOptional.get());
        } else {
            BackpackUser backpackUser = new SimpleBackpackUser();
            backpackUser.addBackpack(1, new SimpleBackpack());

            Backpack enderChest = new SimpleBackpack();

            for (int i = 0; i < 2; i++) {
                enderChest.addRows();
            }

            backpackUser.addBackpack(0, enderChest);

            backpackUserStorage.add(player.getUniqueId(), backpackUser);
        }

        userViewerStorage.findFromData(player.getUniqueId()).ifPresent(userViewer -> userViewerStorage.add(player.getUniqueId(), userViewer));

        user.getClanName().flatMap(clanName -> clanStorage.find(clanName)).ifPresent(
                clan -> Bukkit.getPluginManager().callEvent(new ClanMemberJoinEvent(player, clan))
        );
    }

}