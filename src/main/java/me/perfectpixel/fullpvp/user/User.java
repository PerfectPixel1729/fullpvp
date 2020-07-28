package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Optional;

public interface User extends ConfigurationSerializable {

    Optional<String> getClanName();

    void setClanName(String clanName);

    Coins getCoins();

    Level getLevel();

    Deaths getDeaths();

    Kills getKills();

}