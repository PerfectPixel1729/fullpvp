package me.perfectpixel.fullpvp.user;

import lombok.Getter;

import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SimpleUser implements User {

    private final Coins coins;
    private final Level level;
    private final Deaths deaths;
    private final Kills kills;

    public SimpleUser() {
        coins = new Coins();
        level = new Level();
        deaths = new Deaths();
        kills = new Kills();
    }

    public SimpleUser(Map<String, Object> userMap) {
        coins = new Coins((Integer) userMap.get("coins"));
        level = new Level((Integer) userMap.get("level"));
        deaths = new Deaths((Integer) userMap.get("deaths"));
        kills = new Kills((Integer) userMap.get("kills"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> playerMap = new HashMap<>();

        playerMap.put("coins", coins.get());
        playerMap.put("level", level.get());
        playerMap.put("deaths", deaths.get());
        playerMap.put("kills", kills.get());

        return playerMap;
    }

}