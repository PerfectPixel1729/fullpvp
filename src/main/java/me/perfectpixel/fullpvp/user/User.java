package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface User extends ConfigurationSerializable {

    Optional<String> getClanName();

    void setClanName(String clanName);

    Coins getCoins();

    Level getLevel();

    Deaths getDeaths();

    Kills getKills();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> playerMap = new HashMap<>();

        playerMap.put("coins", getCoins().get());
        playerMap.put("level", getLevel().get());
        playerMap.put("deaths", getDeaths().get());
        playerMap.put("kills", getKills().get());

        getClanName().ifPresent(name -> playerMap.put("name", name));

        return playerMap;
    }

}