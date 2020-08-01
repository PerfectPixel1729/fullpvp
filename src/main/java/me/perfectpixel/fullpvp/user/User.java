package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.statistic.*;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface User extends ConfigurationSerializable, KDR {

    Optional<String> getClanName();

    void setClanName(String clanName);

    Coins getCoins();

    Level getLevel();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> playerMap = new HashMap<>();

        playerMap.put("coins", getCoins().get());
        playerMap.put("level", getLevel().get());
        playerMap.put("deaths", getDeaths().get());
        playerMap.put("kills", getKills().get());

        getClanName().ifPresent(name -> playerMap.put("clanName", name));

        return playerMap;
    }

}