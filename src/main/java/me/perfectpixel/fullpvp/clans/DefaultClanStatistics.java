package me.perfectpixel.fullpvp.clans;

import lombok.Getter;

import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;

@Getter
public class DefaultClanStatistics implements ClanStatistics {

    private final Deaths deaths;
    private final Kills kills;

    public DefaultClanStatistics(int deaths, int kills) {
        this.deaths = new Deaths(deaths);
        this.kills = new Kills(kills);
    }

}