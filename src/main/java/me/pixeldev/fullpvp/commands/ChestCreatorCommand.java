package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.chest.creator.SimpleUserCreator;
import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.chest.creator.UserCreatorInventory;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ACommand(names = {"chestcreator", "ccreator"})
public class ChestCreatorCommand implements CommandClass {

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Inject
    private Cache<UUID, UserCreator> chestCreators;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

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