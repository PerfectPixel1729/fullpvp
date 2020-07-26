package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.loader.ServiceLoader;
import me.perfectpixel.fullpvp.modules.MainModule;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.name.Named;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class FullPVP extends JavaPlugin {

    @Inject
    @Named("chests")
    private Storage<SupplierChest, Location> supplierChestStorage;

    @Inject
    private ServiceLoader serviceLoader;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        serviceLoader.load();
    }

    public void onDisable() {
        supplierChestStorage.saveAll();
    }

}