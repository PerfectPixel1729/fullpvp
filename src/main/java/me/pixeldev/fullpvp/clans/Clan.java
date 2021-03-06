package me.pixeldev.fullpvp.clans;

import me.pixeldev.fullpvp.clans.properties.ClanProperties;
import me.pixeldev.fullpvp.clans.statistics.ClanStatistics;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public interface Clan extends ConfigurationSerializable {

    String getAlias();

    UUID getCreator();

    List<UUID> getMembers();

    ClanProperties getProperties();

    ClanStatistics getStatistics();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> clanSerializable = new HashMap<>();

        clanSerializable.put("alias", getAlias());
        clanSerializable.put("creator", getCreator().toString());

        List<String> members = new ArrayList<>();

        getMembers().forEach(member -> members.add(member.toString()));

        clanSerializable.put("members", members);

        clanSerializable.put("properties", getProperties().serialize());
        clanSerializable.put("statistics", getStatistics().serialize());

        return clanSerializable;
    }

}