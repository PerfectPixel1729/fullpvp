package me.pixeldev.fullpvp.combatlog;

import me.pixeldev.fullpvp.Cache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatLogCache implements Cache<UUID, Integer> {

    private final Map<UUID, Integer> combatLogs = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Integer> get() {
        return combatLogs;
    }

}