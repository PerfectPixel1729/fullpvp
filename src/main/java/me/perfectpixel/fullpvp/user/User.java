package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.statistic.*;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public interface User extends ConfigurationSerializable, KDR {

    Optional<String> getClanName();

    void setClanName(String clanName);

    Coins getCoins();

    Level getLevel();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> playerMap = new LinkedHashMap<>();

        playerMap.put("coins", getCoins().get());
        playerMap.put("level", getLevel().get());
        playerMap.put("deaths", getDeaths().get());
        playerMap.put("kills", getKills().get());

        getClanName().ifPresent(name -> playerMap.put("clan", name));

        return playerMap;
    }

}