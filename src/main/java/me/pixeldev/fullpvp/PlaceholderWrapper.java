package me.pixeldev.fullpvp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.process.annotation.Singleton;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class PlaceholderWrapper extends PlaceholderExpansion {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    @Named("config")
    private FileCreator config;

    @Override
    public @NotNull String getIdentifier() {
        return "fullpvp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PixelDev";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            return "";
        }

        User user = userOptional.get();

        switch (params) {
            case "kills":
                return String.valueOf(user.getKills().get());
            case "level":
                return String.valueOf(user.getLevel().get());
            case "deaths":
                return String.valueOf(user.getDeaths().get());
            case "clan":
                Optional<String> clanNameOptional = user.getClanName();

                if (!clanNameOptional.isPresent()) {
                    return config.getString("clans.default-clan-name");
                }

                String clanName = clanNameOptional.get();

                Optional<Clan> clanOptional = clanStorage.find(clanName);

                if (!clanOptional.isPresent()) {
                    return config.getString("clans.default-clan-name");
                }

                Clan clan = clanOptional.get();

                return clan.getProperties().getColor() + clanName;
            case "kdr":
                return user.getKDR();
            default:
                return "";
        }
    }
}