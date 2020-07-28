package me.perfectpixel.fullpvp.clans;

import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public interface ClanStatistics extends ConfigurationSerializable {

    Deaths getDeaths();

    Kills getKills();

    default String getKDR() {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");

        return (getDeaths().get() == 0) ? String.valueOf(getKills().get()) : decimalFormat.format(getKills().get() / getDeaths().get());
    }

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> statisticsSerializable = new HashMap<>();

        statisticsSerializable.put("kills", getKills().get());
        statisticsSerializable.put("deaths", getDeaths().get());

        return statisticsSerializable;
    }
}