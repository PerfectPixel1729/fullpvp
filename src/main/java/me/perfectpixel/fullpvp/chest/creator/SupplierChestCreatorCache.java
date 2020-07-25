package me.perfectpixel.fullpvp.chest.creator;

import me.perfectpixel.fullpvp.Storage;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SupplierChestCreatorCache implements Storage<Location, UUID> {

    private final Map<UUID, Location> creators = new HashMap<>();

    @Override
    public Map<UUID, Location> get() {
        return creators;
    }

    @Override
    public Optional<Location> find(UUID uuid) {
        return Optional.ofNullable(creators.get(uuid));
    }

    @Override
    public Optional<Location> findFromData(UUID uuid) { return Optional.empty(); }

    @Override
    public void save(UUID uuid) { }

    @Override
    public void remove(UUID uuid) {
        creators.remove(uuid);
    }

    @Override
    public void add(UUID uuid, Location location) {
        creators.put(uuid, location);
    }

    @Override
    public void saveAll() { }

    @Override
    public void loadAll() { }

}