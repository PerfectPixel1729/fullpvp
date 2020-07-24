package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface User extends ConfigurationSerializable {

    Coins getCoins();

    Level getLevel();

    Deaths getDeaths();

    Kills getKills();

}