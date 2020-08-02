package me.pixeldev.fullpvp.modules;

import me.pixeldev.fullpvp.FullPVP;
import me.pixeldev.fullpvp.files.FileBinder;
import me.pixeldev.fullpvp.files.FileCreator;

import me.yushust.inject.bind.AbstractModule;
import org.bukkit.plugin.Plugin;

public class MainModule extends AbstractModule {

    private final FullPVP fullPVP;

    public MainModule(FullPVP fullPVP) {
        this.fullPVP = fullPVP;
    }

    @Override
    protected void configure() {

        FileBinder fileBinder = new FileBinder()
                .bind("data", new FileCreator(fullPVP, "data"))
                .bind("config", new FileCreator(fullPVP, "config"))
                .bind("language", new FileCreator(fullPVP, "language"))
                .bind("chests", new FileCreator(fullPVP, "chests"))
                .bind("menu", new FileCreator(fullPVP, "menu"))
                .bind("clans", new FileCreator(fullPVP, "clans"));

        install(fileBinder.build());
        install(new MessageModule());
        install(new StorageModule());
        install(new MenusModule());
        install(new ServiceModule());

        bind(FullPVP.class).toInstance(fullPVP);
        bind(Plugin.class).to(FullPVP.class);

    }

}