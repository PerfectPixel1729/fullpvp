package me.pixeldev.fullpvp.user;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.files.FileCreator;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorageManager implements Storage<UUID, User> {

    @Inject
    @Named("data")
    private FileCreator data;

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

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

        Object o = data.get("users." + uuid.toString());

        if (o instanceof Map) {
            return Optional.of(new SimpleUser((Map<String, Object>) o));
        } else if (o instanceof ConfigurationSection) {
            return Optional.of(
                    new SimpleUser(
                            data.getConfigurationSection("users." + uuid.toString()).getValues(false)
                    )
            );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(user -> {
            data.set("users." + uuid.toString(), user.serialize());
            data.save();

            remove(uuid);
        });
    }

    @Override
    public void saveObject(UUID key, User value) {
        data.set("users." + key.toString(), value.serialize());
        data.save();

        remove(key);
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
        users.keySet().forEach(this::save);
    }

    @Override
    public void loadAll() {
        if (!data.contains("users")) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> findFromData(player.getUniqueId()).ifPresent(user -> add(player.getUniqueId(), user)));
    }

}