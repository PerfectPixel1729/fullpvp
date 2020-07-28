package me.perfectpixel.fullpvp.files;

import me.yushust.inject.bind.Module;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileBinder {

    private final Map<String, FileCreator> files = new HashMap<>();

    public FileBinder bind(String name, FileCreator file) {
        files.put(name, file);

        return this;
    }

    public Optional<FileCreator> get(String name) {
        return Optional.ofNullable(files.get(name));
    }

    public Module build() {
        return binder -> files.forEach((name, file) -> binder.bind(FileCreator.class).named(name).toInstance(file));
    }

}