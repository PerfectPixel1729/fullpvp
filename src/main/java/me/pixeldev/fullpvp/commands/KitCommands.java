package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.UUID;

@ACommand(names = {"kitcreator", "kcreator"})
public class KitCommands implements CommandClass {

    @Inject
    @Named("kits")
    private Cache<UUID, Integer> kitCreatorsCache;

    @Inject
    private Storage<Integer, Kit> kitStorage;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
    @Named("kit-creator")
    private Menu menu;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) CommandSender sender, Integer level) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("kits.create")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        if (kitStorage.find(level).isPresent()) {
            player.sendMessage(message.getMessage(player, "kit.already-kit"));

            return true;
        }

        kitCreatorsCache.add(player.getUniqueId(), level);

        player.sendMessage(message.getMessage(player, "kit.enter-creator-mode")
                .replace("%level%", level + "")
        );

        player.openInventory(menu.build(player).build());

        return true;
    }

}