package me.perfectpixel.fullpvp.chest.viewer;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChestStorageManager;
import me.perfectpixel.fullpvp.files.FileCreator;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SupplierChestViewerStorageManager implements Storage<UUID, UserViewer> {

    @Inject
    @Named("data")
    private FileCreator data;

    private final Map<UUID, UserViewer> userViewers = new ConcurrentHashMap<>();

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
        if (!data.contains("viewers." + uuid.toString())) {
            return Optional.empty();
        }

        SimpleUserViewer userViewer = new SimpleUserViewer();

        data.getConfigurationSection("viewers." + uuid.toString()).getKeys(false)
                .forEach(name ->
                        SupplierChestStorageManager.findByName(name).ifPresent(supplierChest ->
                                userViewer.getViewed().put(
                                        supplierChest,
                                        data.getInt("viewers." + uuid.toString() + "." + name + ".time")
                                )
                        )
                );

        return Optional.of(userViewer);
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(userViewer -> userViewer.getViewed().forEach((supplierChest, time) -> {
            data.set("viewers." + uuid.toString() + "." + supplierChest.getName() + ".time", time);
        }));

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
    public void saveAll() {
        userViewers.keySet().forEach(this::save);
    }

    @Override
    public void loadAll() {
        if (!data.contains("viewers")) {
            return;
        }

        data.getConfigurationSection("viewers").getKeys(false).forEach(uuid -> findFromData(UUID.fromString(uuid)).ifPresent(userViewer -> add(UUID.fromString(uuid), userViewer)));

        data.save();
    }

}