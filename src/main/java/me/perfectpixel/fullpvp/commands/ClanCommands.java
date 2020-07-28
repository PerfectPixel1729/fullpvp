package me.perfectpixel.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;

import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.DefaultClan;
import me.perfectpixel.fullpvp.files.FileCreator;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"clan", "clans", "clanes"})
@Usage(usage = "§8- §9[create, invite, leave, settings, help]")
public class ClanCommands implements CommandClass {

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
    @Named("config")
    private FileCreator config;

    @ACommand(names = "create")
    @Usage(usage = "§8- §9<name>")
    public boolean runCreateClanCommand(@Injected(true) CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getClanName().isPresent()) {
                player.sendMessage(message.getMessage(player, "clans.commands.already-clan"));

                return true;
            }
        }

        if (clanStorage.find(name).isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.already-exists"));

            return true;
        }

        if (name.length() >= config.getInt("clans.maximum-characters")) {
            player.sendMessage(message.getMessage(player, "clans.commands.name-too-long")
                    .replace("%characters%", config.getInt("clans.maximum-characters") + "")
            );

            return true;
        }

        userOptional.ifPresent(user -> {
            clanStorage.add(name, new DefaultClan(player.getUniqueId(), name));
            user.setClanName(name);

            player.sendMessage(message.getMessage(player, "clans.commands.successfully-create")
                    .replace("%name%", name)
            );
        });

        return true;
    }

}