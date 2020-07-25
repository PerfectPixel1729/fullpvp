package me.perfectpixel.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.creator.SimpleUserCreator;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.chest.creator.UserCreatorInventory;
import me.perfectpixel.fullpvp.menus.Menu;
import me.perfectpixel.fullpvp.message.Message;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

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
    private Storage<UserCreator, UUID> chestCreators;

    @Inject
    private Message message;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) CommandSender sender) {
        Player player = (Player) sender;

        chestCreators.add(
                player.getUniqueId(),
                new SimpleUserCreator(new UserCreatorInventory(
                        player.getInventory().getContents(),
                        player.getInventory().getArmorContents(),
                        player.getGameMode()
                ))
        );

        player.sendMessage(message.getMessage(player, "chest.enter-creator-mode"));

        return true;
    }

}