package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.loader.Service;
import me.perfectpixel.fullpvp.modules.MainModule;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;

import org.bukkit.plugin.java.JavaPlugin;

public class FullPVP extends JavaPlugin {

    @Inject
    private Service service;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        service.load();
    }

    public void onDisable() {
        service.stop();
    }

}