package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.modules.MainModule;
import me.perfectpixel.fullpvp.service.Service;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.name.Named;

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