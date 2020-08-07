package me.pixeldev.fullpvp.kit;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.utils.ItemSerializable;

import org.bukkit.inventory.ItemStack;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KitStorageManager implements Storage<Integer, Kit> {

    @Inject
    @Named("kits")
    private FileCreator kitsData;

    private final Map<Integer, Kit> kits = new ConcurrentHashMap<>();

    @Override
    public Map<Integer, Kit> get() {
        return kits;
    }

    @Override
    public Optional<Kit> find(Integer key) {
        return Optional.ofNullable(kits.get(key));
    }

    @Override
    public Optional<Kit> findFromData(Integer key) {
        if (!kitsData.contains("kits." + key)) {
            return Optional.empty();
        }

        List<ItemStack> armor = new ArrayList<>();
        List<ItemStack> contents = new ArrayList<>();

        for (String armorLevelString : kitsData.getConfigurationSection("kits." + key + ".armor").getKeys(false)) {
            armor.add(ItemSerializable.decodeItem(kitsData.getString("kits." + key + ".armor." + armorLevelString)));
        }

        for (String contentLevelString : kitsData.getConfigurationSection("kits." + key + ".contents").getKeys(false)) {
            contents.add(ItemSerializable.decodeItem(kitsData.getString("kits." + key + ".contents." + contentLevelString)));
        }

        return Optional.of(new SimpleKit(armor, contents));
    }

    @Override
    public void save(Integer key) {
        find(key).ifPresent(kit -> {
            kitsData.set("kits." + key + ".armor", kit.serializeArmor());
            kitsData.set("kits." + key + ".contents", kit.serializeContents());

            remove(key);
        });
    }

    @Override
    public void saveObject(Integer key, Kit value) {
    }

    @Override
    public void remove(Integer key) {
        kits.remove(key);
    }

    @Override
    public void add(Integer key, Kit value) {
        kits.put(key, value);
    }

    @Override
    public void saveAll() {
        kits.keySet().forEach(this::save);

        kitsData.save();
    }

    @Override
    public void loadAll() {
        if (!kitsData.contains("kits")) {
            return;
        }

        kitsData.getConfigurationSection("kits").getKeys(false).forEach(kitLevel -> findFromData(Integer.parseInt(kitLevel)).ifPresent(kit -> kits.put(Integer.parseInt(kitLevel), kit)));
    }

}