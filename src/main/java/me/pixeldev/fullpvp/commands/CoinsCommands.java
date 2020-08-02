package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;

import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"coins", "coin"}, permission = "fullpvp.coins")
@Usage(usage = "§8- §9[add, remove, set]")
public class CoinsCommands implements CommandClass {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    @Delegates
    private Message fileMessage;

    @ACommand(names = "add", permission = "fullpvp.coins.add")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runAddCoinsCommand(@Injected(true) CommandSender sender, String targetName, Integer coins) {
        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-exists"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(fileMessage.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        userOptional.get().getCoins().add(coins);
        sender.sendMessage(fileMessage.getMessage(null, "coins.command.successfully-add")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", targetName)
        );

        return true;
    }

    @ACommand(names = "set", permission = "fullpvp.coins.set")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runSetCoinsCommand(@Injected(true) CommandSender sender, String targetName, Integer coins) {
        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-exists"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(fileMessage.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        userOptional.get().getCoins().set(coins);

        sender.sendMessage(fileMessage.getMessage(null, "coins.command.successfully-set")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", targetName)
        );

        return true;
    }

    @ACommand(names = "remove", permission = "fullpvp.coins.remove")
    @Usage(usage = "§8- §9<targetName> <coins>")
    public boolean runRemoveCoinsCommand(@Injected(true) CommandSender sender, String targetName, Integer coins) {
        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-exists"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(target.getUniqueId());

        if (!userOptional.isPresent()) {
            sender.sendMessage(fileMessage.getMessage(null, "coins.command.user-no-exists"));

            return true;
        }

        User user = userOptional.get();

        if (!user.getCoins().hasCoins()) {
            sender.sendMessage(fileMessage.getMessage(null, "coins.command.no-coins"));

            return true;
        }

        if (!user.getCoins().hasEnoughCoins(coins)) {
            sender.sendMessage(fileMessage.getMessage(null, "coins.command.no-enough-coins"));

            return true;
        }

        user.getCoins().remove(coins);

        sender.sendMessage(fileMessage.getMessage(null, "coins.command.successfully-remove")
                .replace("%coins%", String.valueOf(coins))
                .replace("%target%", targetName)
        );

        return true;
    }

}