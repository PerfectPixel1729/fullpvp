package me.perfectpixel.fullpvp.chest.creator;

import me.perfectpixel.fullpvp.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SupplierChestCreatorCache implements Cache<UUID, UserCreator> {

    private final Map<UUID, UserCreator> creators = new HashMap<>();

    @Override
    public Map<UUID, UserCreator> get() {
        return creators;
    }

}