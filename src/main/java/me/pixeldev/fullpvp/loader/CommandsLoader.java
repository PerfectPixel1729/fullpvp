package me.pixeldev.fullpvp.loader;

import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import me.pixeldev.fullpvp.commands.*;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

@InjectAll
public final class CommandsLoader implements Loader {

    private ChestCreatorCommand chestCreatorCommand;
    private ClanCommands clanCommands;
    private KitCommands kitCommands;
    private SimpleI18n simpleI18n;
    private SupplierKitCommand supplierKitCommand;
    private BackpackCommands backpackCommands;

    @InjectIgnore
    private final ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();

    @InjectIgnore
    private final BukkitCommandManager commandManager = new BukkitCommandManager("FullPVP");

    private void registerCommands(CommandClass... commandClasses) {
        for (CommandClass commandClass : commandClasses) {
            commandManager.registerCommands(builder.fromClass(commandClass));
        }
    }

    @Override
    public void load() {
        commandManager.setI18n(simpleI18n);

        registerCommands(
                chestCreatorCommand,
                clanCommands,
                kitCommands,
                backpackCommands,
                supplierKitCommand
        );
    }

}