package me.perfectpixel.fullpvp.clans.statistics;

import me.perfectpixel.fullpvp.statistic.KDR;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public interface ClanStatistics extends ConfigurationSerializable, KDR {

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> statisticsSerializable = new HashMap<>();

        statisticsSerializable.put("kills", getKills().get());
        statisticsSerializable.put("deaths", getDeaths().get());

        return statisticsSerializable;
    }

}