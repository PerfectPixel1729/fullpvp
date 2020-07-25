package me.perfectpixel.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.menus.Menu;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ACommand(names = {"chestcreator", "ccreator"})
public class ChestCreatorCommand implements CommandClass {

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Inject
    @Named("chests-creators")
    private Storage<Location, UUID> chestCreators;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true)CommandSender sender) {
        Player player = (Player) sender;

        chestCreators.add(player.getUniqueId(), player.getLocation());

        player.openInventory(chestCreatorMenu.build().build());

        return true;
    }

}