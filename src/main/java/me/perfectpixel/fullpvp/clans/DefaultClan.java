package me.perfectpixel.fullpvp.clans;

import lombok.Getter;

import me.perfectpixel.fullpvp.clans.properties.ClanProperties;
import me.perfectpixel.fullpvp.clans.properties.DefaultClanProperties;
import me.perfectpixel.fullpvp.clans.statistics.ClanStatistics;
import me.perfectpixel.fullpvp.clans.statistics.DefaultClanStatistics;

import java.util.*;

@Getter
public class DefaultClan implements Clan {

    private final UUID creator;

    private final ClanProperties properties;
    private final ClanStatistics statistics;

    private final String alias;
    private final List<UUID> members;

    public DefaultClan(UUID creator, String alias) {
        this.creator = creator;
        this.alias = alias;

        properties = new DefaultClanProperties();
        statistics = new DefaultClanStatistics();

        members = new ArrayList<>();
    }

    public DefaultClan(Map<String, Object> clanSerialize) {
        creator = UUID.fromString((String) clanSerialize.get("creator"));
        alias = (String) clanSerialize.get("alias");

        members = (List<UUID>) clanSerialize.get("members");

        properties = new DefaultClanProperties((String) clanSerialize.get("color"), (Boolean) clanSerialize.get("allowedDamage"), (List<String>) clanSerialize.get("messages"));
        statistics = new DefaultClanStatistics((int) clanSerialize.get("deaths"), (int) clanSerialize.get("kills"));
    }

}