package me.perfectpixel.fullpvp.chest.viewer;

import me.perfectpixel.fullpvp.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SupplierChestViewerCache implements Storage<UserViewer, UUID> {

    private final Map<UUID, UserViewer> userViewers = new HashMap<>();

    @Override
    public Map<UUID, UserViewer> get() {
        return userViewers;
    }

    @Override
    public Optional<UserViewer> find(UUID uuid) {
        return Optional.ofNullable(userViewers.get(uuid));
    }

    @Override
    public Optional<UserViewer> findFromData(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void save(UUID uuid) {
        remove(uuid);
    }

    @Override
    public void remove(UUID uuid) {
        userViewers.remove(uuid);
    }

    @Override
    public void add(UUID uuid, UserViewer userViewer) {
        userViewers.put(uuid, userViewer);
    }

    @Override
    public void saveAll() { }

    @Override
    public void loadAll() { }

}