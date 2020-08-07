package me.pixeldev.fullpvp.kit;

import me.pixeldev.fullpvp.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KitCreatorCache implements Cache<UUID, Integer> {

    private final Map<UUID, Integer> kitCreators = new HashMap<>();

    @Override
    public Map<UUID, Integer> get() {
        return kitCreators;
    }
    
}