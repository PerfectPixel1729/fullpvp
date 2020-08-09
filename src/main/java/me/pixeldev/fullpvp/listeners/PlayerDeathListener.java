package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.economy.PlayerEconomy;
import me.pixeldev.fullpvp.event.PlayerRiseExperienceEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;

import me.pixeldev.fullpvp.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@InjectAll
public class PlayerDeathListener implements Listener {

    private Storage<UUID, User> userStorage;
    private Storage<String, Clan> clansStorage;
    private Message message;
    private ItemUtils itemUtils;
    private PlayerEconomy playerEconomy;

    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Named("config")
    private FileCreator config;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        userStorage.find(player.getUniqueId()).ifPresent(user -> {
            user.getDeaths().add(1);

            user.getClanName().flatMap(clanName -> clansStorage.find(clanName)).ifPresent(clan -> clan.getStatistics().getDeaths().add(1));

            player.sendMessage(message.getMessage(player, "events.player-death"));
        });

        List<ItemStack> drops = event.getDrops();

        List<ItemStack> newDrops = drops.stream().filter(item -> !itemUtils.hasNBTTag(item, "default-kit")).collect(Collectors.toList());

        drops.clear();
        drops.addAll(newDrops);

        combatLogCache.remove(player.getUniqueId());

        player.spigot().respawn();

        if (killer == null) {
            return;
        }

        userStorage.find(killer.getUniqueId()).ifPresent(user -> {
            double coins = config.getInt("game.coins-per-kill") + (user.getLevel().get() * 0.25);

            Bukkit.getPluginManager().callEvent(new PlayerRiseExperienceEvent(killer, user, user.getExperience().getCurrent().get() + 1));

            user.getKills().add(1);
            user.getExperience().getCurrent().add(1);

            playerEconomy.depositMoney(player, coins);

            user.getClanName().flatMap(clanName -> clansStorage.find(clanName)).ifPresent(clan -> clan.getStatistics().getKills().add(1));

            killer.giveExp(new Random().nextInt(5));
            killer.sendMessage(message.getMessage(killer, "events.player-kill"));
            killer.sendMessage(message.getMessage(killer, "events.player-gain-coins").replace("%coins%", coins + ""));
        });

        combatLogCache.remove(killer.getUniqueId());
    }

}