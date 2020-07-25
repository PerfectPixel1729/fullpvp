package me.perfectpixel.fullpvp.chest.creator;

import me.perfectpixel.fullpvp.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SupplierChestCreatorCache implements Storage<UserCreator, UUID> {

    private final Map<UUID, UserCreator> creators = new HashMap<>();

    @Override
    public Map<UUID, UserCreator> get() {
        return creators;
    }

    @Override
    public Optional<UserCreator> find(UUID uuid) {
        return Optional.ofNullable(creators.get(uuid));
    }

    @Override
    public Optional<UserCreator> findFromData(UUID uuid) { return Optional.empty(); }

    @Override
    public void save(UUID uuid) { }

    @Override
    public void remove(UUID uuid) {
        creators.remove(uuid);
    }

    @Override
    public void add(UUID uuid, UserCreator location) {
        creators.put(uuid, location);
    }

    @Override
    public void saveAll() { }

    @Override
    public void loadAll() { }

}