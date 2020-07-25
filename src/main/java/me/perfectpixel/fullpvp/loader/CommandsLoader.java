package me.perfectpixel.fullpvp.loader;

import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import me.perfectpixel.fullpvp.commands.ChestCreatorCommand;

import me.yushust.inject.Inject;

public class CommandsLoader implements Loader {

    @Inject
    private ChestCreatorCommand chestCreatorCommand;

    private final ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();
    private final BukkitCommandManager commandManager = new BukkitCommandManager("FullPVP");

    private void registerCommands(CommandClass... commandClasses) {
        for (CommandClass commandClass : commandClasses) {
            commandManager.registerCommands(builder.fromClass(commandClass));
        }
    }

    @Override
    public void load() {
        registerCommands(chestCreatorCommand);
    }

}