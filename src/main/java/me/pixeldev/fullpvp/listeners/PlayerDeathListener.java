package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class PlayerDeathListener implements Listener {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<String, Clan> clansStorage;

    @Inject
    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Inject
    @Named("config")
    private FileCreator config;

    @Inject
    private Message message;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        userStorage.find(player.getUniqueId()).ifPresent(user -> {
            user.getDeaths().add(1);

            user.getClanName().flatMap(clanName -> clansStorage.find(clanName)).ifPresent(clan -> clan.getStatistics().getDeaths().add(1));

            player.sendMessage(message.getMessage(player, "events.player-death"));
        });

        combatLogCache.remove(player.getUniqueId());

        if (killer == null) {
            return;
        }

        userStorage.find(killer.getUniqueId()).ifPresent(user -> {
            int coins = config.getInt("game.coins-per-kill");

            user.getKills().add(1);
            user.getCoins().add(coins);

            user.getClanName().flatMap(clanName -> clansStorage.find(clanName)).ifPresent(clan -> clan.getStatistics().getKills().add(1));

            killer.sendMessage(message.getMessage(killer, "events.player-kill"));
            killer.sendMessage(message.getMessage(killer, "events.player-gain-coins").replace("%coins%", coins + ""));
        });

        combatLogCache.remove(killer.getUniqueId());

        player.spigot().respawn();
    }

}