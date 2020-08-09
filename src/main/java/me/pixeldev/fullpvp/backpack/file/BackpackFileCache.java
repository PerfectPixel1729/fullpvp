package me.pixeldev.fullpvp.backpack.file;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.files.FileCreator;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackFileCache implements Cache<UUID, FileCreator> {

    private final Map<UUID, FileCreator> backpackFiles = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, FileCreator> get() {
        return backpackFiles;
    }

}