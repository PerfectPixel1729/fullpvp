package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileCreator;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Bukkit;

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

        Map<String, Object> statistics = new HashMap<>();

        statistics.put("kills", data.getInt("users." + uuid.toString() + ".kills"));
        statistics.put("coins", data.getInt("users." + uuid.toString() + ".coins"));
        statistics.put("level", data.getInt("users." + uuid.toString() + ".level"));
        statistics.put("deaths", data.getInt("users." + uuid.toString() + ".deaths"));

        if (data.contains("users." + uuid.toString() + ".clanName")) {
            statistics.put("clanName", data.getString("users." + uuid.toString() + ".clanName"));
        }

        return Optional.of(new SimpleUser(statistics));
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
        users.keySet().forEach(this::save);

        data.save();
    }

    @Override
    public void loadAll() {
        if (!data.contains("users")) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> findFromData(player.getUniqueId()).ifPresent(user -> add(player.getUniqueId(), user)));
    }

}