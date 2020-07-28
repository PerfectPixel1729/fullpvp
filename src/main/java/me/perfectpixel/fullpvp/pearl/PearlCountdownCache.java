package me.perfectpixel.fullpvp.pearl;

import me.perfectpixel.fullpvp.Cache;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PearlCountdownCache implements Cache<UUID, Integer> {

    private final Map<UUID, Integer> players = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Integer> get() {
        return players;
    }

    @Override
    public Optional<Integer> find(UUID key) {
        return Optional.ofNullable(players.get(key));
    }

    @Override
    public void remove(UUID key) {
        players.remove(key);
    }

    @Override
    public void add(UUID key, Integer value) {
        players.put(key, value);
    }

    @Override
    public boolean exists(UUID key) {
        return players.containsKey(key);
    }

}