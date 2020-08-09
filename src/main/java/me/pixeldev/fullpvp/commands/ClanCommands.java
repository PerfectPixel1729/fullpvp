package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanUtilities;
import me.pixeldev.fullpvp.clans.DefaultClan;
import me.pixeldev.fullpvp.clans.request.ClanRequest;
import me.pixeldev.fullpvp.clans.request.DefaultClanRequest;
import me.pixeldev.fullpvp.economy.PlayerEconomy;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;
import me.pixeldev.fullpvp.utils.fake.EasyTextComponent;

import org.bukkit.OfflinePlayer;

import team.unnamed.gui.menu.MenuBuilder;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"clan", "clans", "clanes"})
@Usage(usage = "§8- §9[create, invite, leave, settings, help]")
@InjectAll
public class ClanCommands implements CommandClass {

    private Storage<String, Clan> clanStorage;
    private Storage<UUID, User> userStorage;
    private Cache<UUID, ClanRequest> clanRequestCache;
    private Message message;
    private ClanUtilities clanUtilities;
    private EasyTextComponent easyTextComponent;
    private PlayerEconomy playerEconomy;

    @Delegates
    private Message fileMessage;

    @Named("config")
    private FileCreator config;

    @Named("clan-main")
    private Menu clanSettingsMenu;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.user-error"));

            return true;
        }

        User user = userOptional.get();

        Optional<String> clanNameOptional = user.getClanName();

        if (clanNameOptional.isPresent()) {
            MenuBuilder builder = clanSettingsMenu.build(player);

            if (builder == null) {
                player.sendMessage(message.getMessage(player, "clans.commands.error-opening-menu"));

                return true;
            }

            player.openInventory(builder.build());
        } else {
            fileMessage.getMessages(null, "clans.commands.help").forEach(player::sendMessage);
        }

        return true;
    }

    @ACommand(names = "kick")
    public boolean runKickMemberCommand(@Injected(true) CommandSender sender, OfflinePlayer target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans.kick") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        if (target == null) {
            player.sendMessage(message.getMessage(player, "no-player-exists"));

            return true;
        }

        if (!target.isOnline()) {
            player.sendMessage(message.getMessage(player, "no-player-online"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.user-error"));

            return true;
        }

        User user = userOptional.get();

        Optional<String> clanNameOptional = user.getClanName();

        if (!clanNameOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.no-clan"));

            return true;
        }

        String clanName = clanNameOptional.get();

        Optional<Clan> clanOptional = clanStorage.find(clanName);

        if (clanOptional.isPresent()) {
            Clan clan = clanOptional.get();

            if (!clan.getCreator().equals(player.getUniqueId())) {
                player.sendMessage(message.getMessage(player, "clans.no-creator"));

                return true;
            }

            if (!clan.getMembers().contains(target.getUniqueId())) {
                player.sendMessage(message.getMessage(player, "clans.commands.invalid-member"));

                return true;
            }

            clan.getMembers().remove(target.getUniqueId());
            player.sendMessage(message.getMessage(player, "clans.commands.successfully-kick-member")
                    .replace("%target%", target.getName())
            );

            target.getPlayer().sendMessage(message.getMessage(target.getPlayer(), "clans.commands.successfully-kick-member-target")
                    .replace("%clan%", clanName)
            );

            clanUtilities.sendMessageToMembers(clan, fileMessage.getMessage(null, "clans.commands.successfully-kick-member-members")
                    .replace("%target%", target.getName())
            );
        }

        return true;
    }

    @ACommand(names = "help")
    public boolean runHelpClanCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans.help") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        fileMessage.getMessages(null, "clans.commands.help").forEach(player::sendMessage);

        return true;
    }

    @ACommand(names = {"settings", "configuracion"})
    public boolean runSettingsClanCommand(@Injected(true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans.settings") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.user-error"));

            return true;
        }

        User user = userOptional.get();

        Optional<String> clanNameOptional = user.getClanName();

        if (clanNameOptional.isPresent()) {
            MenuBuilder builder = clanSettingsMenu.build(player);

            if (builder == null) {
                player.sendMessage(message.getMessage(player, "clans.commands.error-opening-menu"));

                return true;
            }

            player.openInventory(builder.build());
        }

        return true;
    }

    @ACommand(names = {"crear", "create"})
    @Usage(usage = "§8- §9<name>")
    public boolean runCreateClanCommand(@Injected(true) CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans.create") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.commands.user-error"));

            return true;
        }

        if (clanUtilities.playerHasClan(player)) {
            player.sendMessage(message.getMessage(player, "clans.commands.already-clan"));

            return true;
        }

        if (clanStorage.find(name).isPresent()) {
            player.sendMessage(message.getMessage(player, "clans.already-exists"));

            return true;
        }

        if (!playerEconomy.hasMoney(player)) {
            player.sendMessage(message.getMessage(player, "coins.no-coins"));

            return true;
        }

        int cost = config.getInt("clans.price");

        if (!playerEconomy.hasEnoughMoney(player, cost)) {
            player.sendMessage(message.getMessage(player, "coins.no-enough-coins")
                    .replace("%missing%", String.valueOf(cost - playerEconomy.getMoney(player)))
            );

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

            playerEconomy.withdrawMoney(player, cost);

            player.sendMessage(message.getMessage(player, "clans.commands.successfully-create")
                    .replace("%name%", name)
            );
        });

        return true;
    }

    @ACommand(names = {"invite", "invitar"})
    @Usage(usage = "§8- §9<name>")
    public boolean runInviteClanCommand(@Injected(true) CommandSender sender, OfflinePlayer target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.clans.invite") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        if (target == null) {
            player.sendMessage(message.getMessage(player, "no-player-exists"));

            return true;
        }

        if (!target.isOnline()) {
            player.sendMessage(message.getMessage(player, "no-player-online"));

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

        String clanName = user.getClanName().get();

        int maximumMembers = config.getInt("clans.members.max");

        Optional<Clan> clanOptional = clanStorage.find(user.getClanName().get());

        if (clanOptional.isPresent()) {
            Clan clan = clanOptional.get();

            if (!clan.getCreator().equals(player.getUniqueId())) {
                player.sendMessage(message.getMessage(player, "clans.no-creator"));

                return true;
            }

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

        if (clanUtilities.playerHasClan(target.getPlayer())) {
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
                .replace("%target%", target.getName())
        );

        target.getPlayer().sendMessage(message.getMessage(target.getPlayer(), "clans.commands.successfully-invite-target")
                .replace("%clan%", user.getClanName().get())
                .replace("%time%", requestExpiration + "")
                .replace("%sender%", player.getName())
        );

        Optional<User> userTarget = userStorage.find(target.getUniqueId());

        if (!userTarget.isPresent()) {
            return true;
        }

        TextComponent space = new TextComponent("  •  ");
        space.setColor(ChatColor.GRAY);

        TextComponent accept = easyTextComponent.sendActionMessage(
                target.getPlayer(),
                fileMessage.getMessage(null, "clans.accept-text"),
                true,
                p -> runAcceptCommand(userTarget.get(), target.getPlayer(), clanName)
        );

        TextComponent deny = easyTextComponent.sendActionMessage(
                target.getPlayer(),
                fileMessage.getMessage(null, "clans.deny-text"),
                true,
                p -> runDenyCommand(target.getPlayer(), clanName)
        );

        target.getPlayer().spigot().sendMessage(accept, space, deny);

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

    private void runAcceptCommand(User user, Player player, String clanName) {
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
                user.setClanName(clanName);
            });
        }
    }

    private void runDenyCommand(Player player, String clanName) {
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
    }

}