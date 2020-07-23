package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.files.FileBinder;
import me.perfectpixel.fullpvp.files.FileManager;

import me.yushust.inject.bind.AbstractModule;

public class MainModule extends AbstractModule {

    private final FullPVP fullPVP;

    public MainModule(FullPVP fullPVP) {
        this.fullPVP = fullPVP;
    }

    @Override
    protected void configure() {

        FileBinder fileBinder = new FileBinder()
                .bind("data", new FileManager(fullPVP, "data"))
                .bind("config", new FileManager(fullPVP, "config"))
                .bind("language", new FileManager(fullPVP, "language"));

        install(fileBinder.build());
        install(new StorageModule());

        bind(FullPVP.class).toInstance(fullPVP);

    }

}