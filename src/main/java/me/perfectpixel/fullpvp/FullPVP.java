package me.perfectpixel.fullpvp;

import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.modules.MainModule;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.name.Named;

import org.bukkit.plugin.java.JavaPlugin;

public class FullPVP extends JavaPlugin {

    @Inject
    @Named("chests")
    private Storage<SupplierChest, String> supplierChestStorage;

    @Inject
    private EventsLoader eventsLoader;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        registerCommands();

        eventsLoader.register();

        supplierChestStorage.loadAll();
    }

    public void onDisable() {
        supplierChestStorage.saveAll();
    }

    private void registerCommands() {
        ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();
        BukkitCommandManager commandManager = new BukkitCommandManager(getName());
    }

}