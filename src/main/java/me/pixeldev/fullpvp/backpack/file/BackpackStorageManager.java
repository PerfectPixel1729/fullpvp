package me.pixeldev.fullpvp.backpack.file;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.Backpack;
import me.pixeldev.fullpvp.backpack.SimpleBackpack;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;
import me.pixeldev.fullpvp.backpack.user.SimpleBackpackUser;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.utils.ItemSerializable;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import team.unnamed.inject.Inject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackStorageManager implements Storage<UUID, BackpackUser> {

    @Inject
    private Cache<UUID, FileCreator> backpackFileCache;

    @Inject
    private Plugin plugin;

    private final Map<UUID, BackpackUser> backpackUsers = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, BackpackUser> get() {
        return backpackUsers;
    }

    @Override
    public Optional<BackpackUser> find(UUID key) {
        return Optional.ofNullable(backpackUsers.get(key));
    }

    @Override
    public Optional<BackpackUser> findFromData(UUID key) {
        Optional<FileCreator> fileOptional = backpackFileCache.find(key);

        if (!fileOptional.isPresent()) {
            return Optional.empty();
        }

        FileCreator file = fileOptional.get();

        ConfigurationSection section = file.getConfigurationSection("");

        if (section == null || section.getKeys(false).isEmpty()) {
            return Optional.empty();
        }

        Map<Integer, Backpack> backpacks = new HashMap<>();

        for (String position : section.getKeys(false)) {
            Map<Integer, ItemStack> backpackContents = new HashMap<>();

            for (String slot : file.getConfigurationSection(position + ".contents").getKeys(false)) {
                backpackContents.put(Integer.parseInt(slot), ItemSerializable.decodeItem(file.getString(position + ".contents." + slot)));
            }

            backpacks.put(Integer.parseInt(position), new SimpleBackpack(backpackContents, file.getInt(position + ".rows")));
        }

        return Optional.of(new SimpleBackpackUser(backpacks));
    }

    @Override
    public void save(UUID key) {
        backpackFileCache.find(key).ifPresent(fileCreator -> find(key).ifPresent(backpackUser -> {
            backpackUser.getBackpacks().forEach((position, backpack) -> {
                fileCreator.set(position + ".rows", backpack.getRows());
                fileCreator.set(position + ".contents", backpack.serialize());
            });

            fileCreator.save();
            remove(key);
            backpackFileCache.remove(key);
        }));
    }

    @Override
    public void saveObject(UUID key, BackpackUser value) {
        backpackFileCache.find(key).ifPresent(fileCreator -> {
            value.getBackpacks().forEach((position, backpack) -> {
                fileCreator.set(position + ".rows", backpack.getRows());
                fileCreator.set(position + ".contents", backpack.serialize());
            });

            fileCreator.save();
            remove(key);
            backpackFileCache.remove(key);
        });
    }

    @Override
    public void remove(UUID key) {
        backpackFileCache.remove(key);
        backpackUsers.remove(key);
    }

    @Override
    public void add(UUID key, BackpackUser value) {
        backpackUsers.put(key, value);
    }

    @Override
    public void saveAll() {
        backpackUsers.keySet().forEach(this::save);
    }

    @Override
    public void loadAll() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID uuid = player.getUniqueId();

            backpackFileCache.add(uuid, new FileCreator(plugin, uuid.toString(), ".yml", "backpacks"));

            findFromData(uuid).ifPresent(backpackUser -> add(uuid, backpackUser));
        });
    }

}