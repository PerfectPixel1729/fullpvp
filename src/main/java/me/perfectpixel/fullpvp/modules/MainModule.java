package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.FullPVP;
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
                .bind("language", new FileManager(fullPVP, "language"))
                .bind("chests", new FileManager(fullPVP, "chests"))
                .bind("menu", new FileManager(fullPVP, "menu"));

        install(fileBinder.build());
        install(new MessageModule());
        install(new StorageModule());
        install(new MenusModule());

        bind(FullPVP.class).toInstance(fullPVP);

    }

}