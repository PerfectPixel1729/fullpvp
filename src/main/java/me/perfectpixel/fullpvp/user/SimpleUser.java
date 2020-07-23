package me.perfectpixel.fullpvp.user;

import lombok.Getter;
import me.perfectpixel.fullpvp.statistic.Coins;
import me.perfectpixel.fullpvp.statistic.Deaths;
import me.perfectpixel.fullpvp.statistic.Kills;
import me.perfectpixel.fullpvp.statistic.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class SimpleUser implements User {

    private final UUID id;

    private final Coins coins;
    private final Level level;
    private final Deaths deaths;
    private final Kills kills;

    public SimpleUser(UUID id) {
        this.id = id;
        coins = new Coins();
        level = new Level();
        deaths = new Deaths();
        kills = new Kills();
    }

    public SimpleUser(UUID id, Map<String, Object> userMap) {
        this.id = id;

        coins = new Coins((Integer) userMap.get("coins"));
        level = new Level((Integer) userMap.get("level"));
        deaths = new Deaths((Integer) userMap.get("deaths"));
        kills = new Kills((Integer) userMap.get("kills"));
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(id));
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