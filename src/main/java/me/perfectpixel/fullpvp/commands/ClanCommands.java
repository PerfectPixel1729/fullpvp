package me.perfectpixel.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.ClanUtilities;
import me.perfectpixel.fullpvp.clans.DefaultClan;
import me.perfectpixel.fullpvp.clans.request.ClanRequest;
import me.perfectpixel.fullpvp.clans.request.DefaultClanRequest;
import me.perfectpixel.fullpvp.files.FileCreator;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"clan", "clans", "clanes"}, permission = "fullpvp.clans")
@Usage(usage = "§8- §9[create, invite, leave, settings, help]")
public class ClanCommands implements CommandClass {

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Cache<UUID, ClanRequest> clanRequestCache;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
    @Named("config")
    private FileCreator config;

    @Inject
    private ClanUtilities clanUtilities;

    @ACommand(names = {"crear", "create"}, permission = "fullpvp.clans.create")
    @Usage(usage = "§8- §9<name>")
    public boolean runCreateClanCommand(@Injected(true) CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (clanUtilities.playerHasClan(player)) {
            player.sendMessage(message.getMessage(player, "clans.commands.already-clan"));

            return true;
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

    @ACommand(names = {"invite", "invitar"}, permission = "fullpvp.clans.invite")
    @Usage(usage = "§8- §9<name>")
    public boolean runInviteClanCommand(@Injected(true) CommandSender sender, String playerName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        Player target = Bukkit.getPlayerExact(playerName);

        if (target == null) {
            player.sendMessage(message.getMessage(player, "no-player-exists"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.user-error"));

            return true;
        }

        User user = userOptional.get();

        if (!user.getClanName().isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.no-clan"));

            return true;
        }

        int maximumMembers = config.getInt("clans.members.max");

        Optional<Clan> clanOptional = clanStorage.find(user.getClanName().get());

        if (clanOptional.isPresent()) {
            Clan clan = clanOptional.get();

            if (clan.getMembers().contains(target.getUniqueId())) {
                player.sendMessage(message.getMessage(player, "clans.commands.already-member"));

                return true;
            }

            if (clan.getMembers().size() >= maximumMembers) {
                player.sendMessage(message.getMessage(player, "clans.commands.maximum-members")
                        .replace("%limit%", maximumMembers + "")
                );

                return true;
            }
        }

        if (clanUtilities.playerHasClan(target)) {
            player.sendMessage(message.getMessage(player, "clans.commands.already-clan-target"));

            return true;
        }

        Optional<ClanRequest> clanRequestOptional = clanRequestCache.find(target.getUniqueId());
        int requestExpiration = config.getInt("clans.request-expiration");

        if (clanRequestOptional.isPresent()) {
            if (clanRequestOptional.get().getClanRequests().containsKey(user.getClanName().get())) {
                player.sendMessage(message.getMessage(player, "clans.commands.already-invite"));

                return true;
            }

            clanRequestOptional.get().getClanRequests().put(user.getClanName().get(), requestExpiration);
        } else {
            ClanRequest clanRequest = new DefaultClanRequest();

            clanRequest.getClanRequests().put(user.getClanName().get(), requestExpiration);

            clanRequestCache.add(target.getUniqueId(), clanRequest);
        }

        player.sendMessage(message.getMessage(player, "clans.commands.successfully-invite-sender")
                .replace("%target%", playerName)
        );

        target.sendMessage(message.getMessage(target, "clans.commands.successfully-invite-target")
                .replace("%clan%", user.getClanName().get())
                .replace("%time%", requestExpiration + "")
                .replace("%sender%", player.getName())
        );

        return true;
    }

    @ACommand(names = {"accept", "aceptar"}, permission = "fullpvp.clans.accept")
    @Usage(usage = "§8- §9<clan name>")
    public boolean runAcceptClanCommand(@Injected(true) CommandSender sender, String clanName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (prepareClanAction(player, clanName)) {
            clanUtilities.sendMessageToMembers(clanName, fileMessage.getMessage(null, "clans.player-join-clan")
                    .replace("%player%", player.getName())
            );

            clanStorage.find(clanName).ifPresent(clan -> {
                clan.getMembers().add(player.getUniqueId());

                Bukkit.getPlayer(clan.getCreator()).sendMessage(fileMessage.getMessage(null, "clans.commands.successfully-accept-request")
                        .replace("%player%", player.getName())
                );

                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);

                player.sendMessage(message.getMessage(player, "clans.commands.successfully-join")
                        .replace("%clan%", clanName)
                );

                clanRequestCache.find(player.getUniqueId()).ifPresent(clanRequest -> clanRequest.getClanRequests().remove(clanName));
                userStorage.find(player.getUniqueId()).ifPresent(user -> user.setClanName(clanName));
            });

            return true;
        }

        return true;
    }

    @ACommand(names = {"deny", "denegar"}, permission = "fullpvp.clans.deny")
    @Usage(usage = "§8- §9<clan name>")
    public boolean runDenyClanCommand(@Injected(true) CommandSender sender, String clanName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (prepareClanAction(player, clanName)) {
            clanStorage.find(clanName).ifPresent(clan -> {
                Bukkit.getPlayer(clan.getCreator()).sendMessage(fileMessage.getMessage(null, "clans.commands.deny-request-sender")
                        .replace("%target%", player.getName())
                );

                player.sendMessage(message.getMessage(player, "clans.commands.deny-request-target")
                        .replace("%clan%", clanName)
                );

                clanRequestCache.find(player.getUniqueId()).ifPresent(clanRequest -> clanRequest.getClanRequests().remove(clanName));
            });
        }

        return true;
    }

    private boolean prepareClanAction(Player player, String clanName) {
        Optional<ClanRequest> clanRequestOptional = clanRequestCache.find(player.getUniqueId());

        if (!clanRequestOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.no-pending-requests"));

            return false;
        } else {
            if (clanRequestOptional.get().getClanRequests().isEmpty()) {
                player.sendMessage(message.getMessage(player, "clans.commands.no-pending-requests"));

                return false;
            }

            if (!clanRequestOptional.get().getClanRequests().containsKey(clanName)) {
                player.sendMessage(message.getMessage(player, "clans.commands.no-pending-request"));

                return false;
            }

            if (clanUtilities.playerHasClan(player)) {
                player.sendMessage(message.getMessage(player, "clans.already-clan"));

                return false;
            }
        }

        return true;
    }

}