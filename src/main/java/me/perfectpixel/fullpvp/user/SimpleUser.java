package me.perfectpixel.fullpvp.user;

import lombok.Getter;

import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import java.util.Map;
import java.util.Optional;

@Getter
public class SimpleUser implements User {

    private String clanName;
    private final Coins coins;
    private final Level level;
    private final Deaths deaths;
    private final Kills kills;

    public SimpleUser() {
        coins = new Coins();
        level = new Level();
        deaths = new Deaths();
        kills = new Kills();
        clanName = null;
    }

    public SimpleUser(Map<String, Object> userMap) {
        coins = new Coins((Integer) userMap.get("coins"));
        level = new Level((Integer) userMap.get("level"));
        deaths = new Deaths((Integer) userMap.get("deaths"));
        kills = new Kills((Integer) userMap.get("kills"));
        clanName = (String) userMap.get("clan");
    }

    @Override
    public Optional<String> getClanName() {
        return Optional.ofNullable(clanName);
    }

    @Override
    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

}