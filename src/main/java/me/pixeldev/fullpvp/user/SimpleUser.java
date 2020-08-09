package me.pixeldev.fullpvp.user;

import lombok.Getter;

import me.pixeldev.fullpvp.statistic.*;

import java.util.Map;
import java.util.Optional;

@Getter
public class SimpleUser implements User {

    private String clanName;
    private int kitLevel;

    private final Level level;
    private final Deaths deaths;
    private final Kills kills;
    private final Experience experience;

    public SimpleUser() {
        level = new Level();
        deaths = new Deaths();
        kills = new Kills();
        experience = new Experience();
        clanName = null;
        kitLevel = 0;
    }

    public SimpleUser(Map<String, Object> userMap) {
        level = new Level((Integer) userMap.get("level"));
        deaths = new Deaths((Integer) userMap.get("deaths"));
        kills = new Kills((Integer) userMap.get("kills"));
        experience = new Experience(kills, (Integer) userMap.get("experience-to"));
        clanName = (String) userMap.get("clan");
        kitLevel = (int) userMap.get("kit-level");
    }

    @Override
    public void setKitLevel(int kitLevel) {
        this.kitLevel = kitLevel;
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