package me.perfectpixel.fullpvp.message.placeholder;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.user.User;
import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;
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