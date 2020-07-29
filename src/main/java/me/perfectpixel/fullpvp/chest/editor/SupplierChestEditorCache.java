package me.perfectpixel.fullpvp.chest.editor;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.chest.SupplierChest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SupplierChestEditorCache implements Cache<UUID, SupplierChest> {

    private final Map<UUID, SupplierChest> usersEditors = new HashMap<>();

    @Override
    public Map<UUID, SupplierChest> get() {
        return usersEditors;
    }

}