package me.pixeldev.fullpvp.backpack;

import me.pixeldev.fullpvp.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackpackEditorCache implements Cache<UUID, Backpack> {

    private final Map<UUID, Backpack> backpackEditors = new HashMap<>();

    @Override
    public Map<UUID, Backpack> get() {
        return backpackEditors;
    }

}