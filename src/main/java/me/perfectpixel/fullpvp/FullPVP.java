package me.perfectpixel.fullpvp;

import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import me.perfectpixel.fullpvp.listeners.PlayerJoinListener;
import me.perfectpixel.fullpvp.listeners.PlayerQuitListener;
import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class FullPVP extends JavaPlugin {

    @Inject
    private PlayerJoinListener playerJoinListener;

    @Inject
    private PlayerQuitListener playerQuitListener;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        registerListeners(playerJoinListener, playerQuitListener);

        registerCommands();
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();
        BukkitCommandManager commandManager = new BukkitCommandManager(getName());
    }

}