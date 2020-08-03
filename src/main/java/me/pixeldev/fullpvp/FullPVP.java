package me.pixeldev.fullpvp;

import me.pixeldev.fullpvp.modules.MainModule;
import me.pixeldev.fullpvp.service.Service;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.name.Named;

import org.bukkit.plugin.java.JavaPlugin;

public final class FullPVP extends JavaPlugin {

    @Inject
    @Named("fullpvp-service")
    private Service fullpvpService;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        fullpvpService.start();
    }

    public void onDisable() {
        fullpvpService.stop();
    }

}