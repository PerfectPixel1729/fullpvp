package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface User extends ConfigurationSerializable {

    UUID getID();

    Optional<Player> getPlayer();

    Coins getCoins();

    Level getLevel();

    Deaths getDeaths();

    Kills getKills();

}