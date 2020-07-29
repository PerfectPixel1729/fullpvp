package me.perfectpixel.fullpvp.pearl;

import me.perfectpixel.fullpvp.Cache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PearlCountdownCache implements Cache<UUID, Integer> {

    private final Map<UUID, Integer> players = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Integer> get() {
        return players;
    }

}