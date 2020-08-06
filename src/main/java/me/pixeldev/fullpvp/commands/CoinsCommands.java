package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.InjectAll;

import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"coins", "coin"}, permission = "fullpvp.coins")
@Usage(usage = "§8- §9[add, remove, set]")
@InjectAll
public class CoinsCommands implements CommandClass {

    private Storage<UUID, User> userStorage;
    private Message message;

    @ACommand(names = "")
    public boolean runMainCoinsCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(message.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(message.getMessage(player, "coins.command.user-no-exists"));

            return true;
        }

        player.sendMessage(message.getMessage(player, "coins.command.show-coins"));

        return true;
    }

    @ACommand(names = "add", permission = "fullpvp.coins.add")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runAddCoinsCommand(@Injected(true) CommandSender sender, OfflinePlayer target, Integer coins) {
        if (target == null) {
            sender.sendMessage(message.getMessage(null, "no-player-exists"));

            return true;
        }

        if (!target.isOnline()) {
            sender.sendMessage(message.getMessage(null, "no-player-online"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(message.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        userOptional.get().getCoins().add(coins);
        sender.sendMessage(message.getMessage(null, "coins.command.successfully-add")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", target.getName())
        );

        return true;
    }

    @ACommand(names = "set", permission = "fullpvp.coins.set")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runSetCoinsCommand(@Injected(true) CommandSender sender, OfflinePlayer target, Integer coins) {
        if (target == null) {
            sender.sendMessage(message.getMessage(null, "no-player-exists"));

            return true;
        }

        if (!target.isOnline()) {
            sender.sendMessage(message.getMessage(null, "no-player-online"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(message.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        userOptional.get().getCoins().set(coins);

        sender.sendMessage(message.getMessage(null, "coins.command.successfully-set")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", target.getName())
        );

        return true;
    }

    @ACommand(names = "remove", permission = "fullpvp.coins.remove")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runRemoveCoinsCommand(@Injected(true) CommandSender sender, OfflinePlayer target, Integer coins) {
        if (target == null) {
            sender.sendMessage(message.getMessage(null, "no-player-exists"));

            return true;
        }

        if (!target.isOnline()) {
            sender.sendMessage(message.getMessage(null, "no-player-online"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(message.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        User user = userOptional.get();

        if (!user.getCoins().hasCoins()) {
            sender.sendMessage(message.getMessage(null, "coins.command.no-coins"));

            return true;
        }

        if (!user.getCoins().hasEnoughCoins(coins)) {
            sender.sendMessage(message.getMessage(null, "coins.command.no-enough-coins"));

            return true;
        }

        user.getCoins().remove(coins);

        sender.sendMessage(message.getMessage(null, "coins.command.successfully-remove")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", target.getName())
        );

        return true;
    }

}