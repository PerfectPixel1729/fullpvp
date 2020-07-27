package me.perfectpixel.fullpvp.chest.editor;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.chest.SupplierChest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SupplierChestEditorCache implements Cache<UUID, SupplierChest> {

    private final Map<UUID, SupplierChest> usersEditors = new HashMap<>();

    @Override
    public Map<UUID, SupplierChest> get() {
        return usersEditors;
    }

    @Override
    public Optional<SupplierChest> find(UUID key) {
        return Optional.ofNullable(usersEditors.get(key));
    }

    @Override
    public void remove(UUID key) {
        usersEditors.remove(key);
    }

    @Override
    public void add(UUID key, SupplierChest value) {
        usersEditors.put(key, value);
    }

    @Override
    public boolean exists(UUID key) {
        return usersEditors.containsKey(key);
    }

}