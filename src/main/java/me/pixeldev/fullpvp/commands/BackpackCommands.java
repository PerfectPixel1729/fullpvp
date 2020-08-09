package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.Backpack;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;
import me.pixeldev.fullpvp.economy.PlayerEconomy;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"backpack", "bp", "bpack"})
@InjectAll
public class BackpackCommands implements CommandClass {

    private Storage<UUID, BackpackUser> backpackUserStorage;
    private Cache<UUID, Backpack> backpackEditorCache;
    private Message message;
    private MessageMenu messageMenu;
    private PlayerEconomy playerEconomy;

    @Named("config")
    private FileCreator config;

    @Delegates
    private Message fileMessage;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.backpack") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        message.getMessages(player, "backpack.help").forEach(player::sendMessage);

        return true;
    }

    @ACommand(names = {"open", "abrir"})
    public boolean runOpenBackpackCommand(@Injected(true) CommandSender sender, Integer number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.backpack.open") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<BackpackUser> backpackUserOptional = backpackUserStorage.find(player.getUniqueId());

        if (!backpackUserOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.user-error"));

            return true;
        }

        Optional<Backpack> backpackOptional = backpackUserOptional.get().getBackpack(number);

        if (!backpackOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.invalid-backpack"));

            return true;
        }

        Backpack backpack = backpackOptional.get();

        Inventory inventory = Bukkit.createInventory(null, 9 * backpack.getRows(), messageMenu.getTitle("backpack")
                .replace("%position%", number + "")
        );

        backpack.getContents().forEach(inventory::setItem);
        backpackEditorCache.add(player.getUniqueId(), backpack);

        player.openInventory(inventory);
        player.sendMessage(message.getMessage(player, "backpack.open-backpack")
                .replace("%position%", number + "")
        );

        return true;
    }

    @ACommand(names = {"clear", "limpiar"})
    public boolean runClearBackpackCommand(@Injected(true) CommandSender sender, Integer number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.backpack.upgrade") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<BackpackUser> backpackUserOptional = backpackUserStorage.find(player.getUniqueId());

        if (!backpackUserOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.user-error"));

            return true;
        }

        Optional<Backpack> backpackOptional = backpackUserOptional.get().getBackpack(number);

        if (!backpackOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.invalid-backpack"));

            return true;
        }

        Backpack backpack = backpackOptional.get();

        backpack.getContents().clear();
        player.sendMessage(message.getMessage(player, "backpack.successfully-clear")
                .replace("%position%", number + "")
        );

        return true;
    }

    @ACommand(names = {"upgrade", "mejorar"})
    public boolean runUpgradeBackpackCommand(@Injected(true) CommandSender sender, Integer number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.backpack.upgrade") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<BackpackUser> backpackUserOptional = backpackUserStorage.find(player.getUniqueId());

        if (!backpackUserOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.user-error"));

            return true;
        }

        Optional<Backpack> backpackOptional = backpackUserOptional.get().getBackpack(number);

        if (!backpackOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "backpack.invalid-backpack"));

            return true;
        }

        Backpack backpack = backpackOptional.get();

        if (backpack.getRows() == 6) {
            player.sendMessage(message.getMessage(player, "backpack.maximum-tier"));

            return true;
        }

        if (!playerEconomy.hasMoney(player)) {
            player.sendMessage(message.getMessage(player, "coins.no-coins"));

            return true;
        }

        int cost = config.getInt("backpack.upgrades.tier-" + (backpack.getRows() + 1));

        if (!playerEconomy.hasEnoughMoney(player, cost)) {
            player.sendMessage(message.getMessage(player, "coins.no-enough-coins")
                    .replace("%missing%", String.valueOf(cost - playerEconomy.getMoney(player)))
            );

            return true;
        }

        backpack.addRows();

        playerEconomy.withdrawMoney(player, cost);

        player.sendMessage(message.getMessage(player, "backpack.successfully-upgrade")
                .replace("%position%", number + "")
                .replace("%rows%", backpack.getRows() + "")
        );

        return true;
    }


}