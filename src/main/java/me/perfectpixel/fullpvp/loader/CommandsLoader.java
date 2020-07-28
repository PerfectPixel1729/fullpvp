package me.perfectpixel.fullpvp.loader;

import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import me.perfectpixel.fullpvp.commands.ChestCreatorCommand;

import me.perfectpixel.fullpvp.commands.ClanCommands;
import me.perfectpixel.fullpvp.commands.CoinsCommands;
import me.perfectpixel.fullpvp.commands.SimpleI18n;
import me.yushust.inject.Inject;

public final class CommandsLoader implements Loader {

    @Inject
    private ChestCreatorCommand chestCreatorCommand;

    @Inject
    private CoinsCommands coinsCommands;

    @Inject
    private ClanCommands clanCommands;

    @Inject
    private SimpleI18n simpleI18n;

    private final ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();
    private final BukkitCommandManager commandManager = new BukkitCommandManager("FullPVP");

    private void registerCommands(CommandClass... commandClasses) {
        for (CommandClass commandClass : commandClasses) {
            commandManager.registerCommands(builder.fromClass(commandClass));
        }
    }

    @Override
    public void load() {
        commandManager.setI18n(simpleI18n);

        registerCommands(chestCreatorCommand, coinsCommands, clanCommands);
    }

}