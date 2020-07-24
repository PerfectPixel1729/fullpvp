package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import java.util.*;

public class UserStorageManager implements Storage<User, UUID> {

    @Inject
    @Named("data")
    private FileManager data;

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public Map<UUID, User> get() {
        return users;
    }

    @Override
    public Optional<User> find(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }

    @Override
    public Optional<User> findFromData(UUID uuid) {
        if (!data.contains("users." + uuid.toString())) {
            return Optional.empty();
        }

        return Optional.of(new SimpleUser((Map<String, Object>) data.get("users." + uuid.toString())));
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(user -> data.set("users." + uuid.toString(), user.serialize()));

        remove(uuid);
    }

    @Override
    public void remove(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public void add(UUID uuid, User user) {
        users.put(uuid, user);
    }

    @Override
    public void saveAll() {
        users.forEach(((uuid, user) -> save(uuid)));

        data.save();
    }

    @Override
    public void loadAll() {
        if (!data.contains("users")) {
            return;
        }

        data.getConfigurationSection("users").getKeys(false).forEach(uuid -> add(UUID.fromString(uuid), new SimpleUser((Map<String, Object>) data.get("users." + uuid))));
    }

}