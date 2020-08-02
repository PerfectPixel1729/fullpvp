package me.pixeldev.fullpvp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.pixeldev.fullpvp.user.User;

import me.yushust.inject.Inject;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlaceHolderHandler extends PlaceholderExpansion {

    @Inject
    private Storage<UUID, User> userStorage;

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "fullpvp";
    }

    @Override
    public String getAuthor() {
        return "PerfectPixel";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        if (player == null) {
            return "";
        }

        if (!userStorage.find(player.getUniqueId()).isPresent()) {
            return "";
        }

        User user = userStorage.find(player.getUniqueId()).get();

        switch (identifier) {
            case "kills":
                return user.getKills().get() + "";
            case "deaths":
                return user.getDeaths().get() + "";
            case "level":
                return user.getLevel().get() + "";
            case "coins":
                return user.getCoins().get() + "";
            default:
                return "";
        }
    }

}