package me.perfectpixel.fullpvp.chest.creator;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SupplierChestCreatorCache implements Cache<UUID, UserCreator> {

    private final Map<UUID, UserCreator> creators = new HashMap<>();

    @Override
    public Map<UUID, UserCreator> get() {
        return creators;
    }

    @Override
    public Optional<UserCreator> find(UUID key) {
        return Optional.ofNullable(creators.get(key));
    }

    @Override
    public void remove(UUID key) {
        creators.remove(key);
    }

    @Override
    public void add(UUID key, UserCreator value) {
        creators.put(key, value);
    }

    @Override
    public boolean exists(UUID key) {
        return creators.containsKey(key);
    }

}