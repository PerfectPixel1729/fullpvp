package me.perfectpixel.fullpvp.clans;

import lombok.Getter;

import java.util.*;

@Getter
public class DefaultClan implements Clan {

    private final UUID creator;

    private final ClanProperties properties;
    private final ClanStatistics statistics;

    private final String alias;
    private final Set<UUID> members = new HashSet<>();

    public DefaultClan(UUID creator, String alias) {
        this(creator, alias, 0, 0, "GRAY", false, new ArrayList<>());
    }

    public DefaultClan(UUID creator, String alias, int deaths, int kills, String color, boolean allowedDamage, List<String> messages) {
        this.creator = creator;
        this.alias = alias;

        properties = new DefaultClanProperties(color, allowedDamage, messages);
        statistics = new DefaultClanStatistics(deaths, kills);
    }

}