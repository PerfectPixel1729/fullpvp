package me.perfectpixel.fullpvp.clans;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface Clan extends ConfigurationSerializable {

    String getAlias();

    UUID getCreator();

    Set<UUID> getMembers();

    ClanProperties getProperties();

    ClanStatistics getStatistics();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> clanSerializable = new HashMap<>();

        clanSerializable.put("alias", getAlias());
        clanSerializable.put("creator", getCreator());
        clanSerializable.put("members", getMembers());

        clanSerializable.put("properties", getProperties().serialize());
        clanSerializable.put("statistics", getStatistics().serialize());

        return clanSerializable;
    }

}