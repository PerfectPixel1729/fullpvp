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

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ACommand(names = {"chestcreator", "ccreator"})
@InjectAll
public class ChestCreatorCommand implements CommandClass {

    private Cache<UUID, UserCreator> chestCreators;
    private Message message;

    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Delegates
    private Message fileMessage;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.chestcreator") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

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