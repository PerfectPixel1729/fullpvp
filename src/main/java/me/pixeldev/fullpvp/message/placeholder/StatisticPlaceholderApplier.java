package me.pixeldev.fullpvp.message.placeholder;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.user.User;
import me.yushust.inject.Inject;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatisticPlaceholderApplier implements PlaceholderApplier {

    @Inject
    private Storage<UUID, User> userStorage;

    @Override
    public String setPlaceHolders(Player player, String text) {

        if (userStorage.find(player.getUniqueId()).isPresent()) {
            User user = userStorage.find(player.getUniqueId()).get();

            return text
                    .replace("%fullpvp_kills%", user.getKills().get() + "")
                    .replace("%fullpvp_deaths%", user.getDeaths().get() + "")
                    .replace("%fullpvp_coins%", user.getCoins().get() + "")
                    .replace("%fullpvp_level", user.getLevel().get() + "");
        }

        return text;
    }

}