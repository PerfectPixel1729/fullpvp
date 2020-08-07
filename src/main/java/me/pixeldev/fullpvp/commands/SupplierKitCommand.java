package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.pixeldev.fullpvp.BasicManager;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.message.Message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unnamed.inject.InjectAll;

import java.util.UUID;

@ACommand(names = {"kitsuppliercreator", "ksuppliercreator", "kscreator"})
@InjectAll
public class SupplierKitCommand implements CommandClass {

    private BasicManager<UUID> supplierKitCreatorCache;
    private Message message;

    @Delegates
    private Message fileMessage;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("kits.create")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        if (!supplierKitCreatorCache.exists(player.getUniqueId())) {
            supplierKitCreatorCache.add(player.getUniqueId());
            player.sendMessage(message.getMessage(player, "kit.supplier.enter-creator-mode"));
        } else {
            supplierKitCreatorCache.remove(player.getUniqueId());
            player.sendMessage(message.getMessage(player, "kit.supplier.exit-creator-mode"));
        }

        return true;
    }

}