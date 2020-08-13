package me.pixeldev.fullpvp.listeners.clan;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanMessagesFormatter;
import me.pixeldev.fullpvp.clans.ClanUtilities;
import me.pixeldev.fullpvp.clans.request.ClanRequest;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.event.clan.ClanChatEvent;
import me.pixeldev.fullpvp.event.clan.ClanEditMessagesEvent;
import me.pixeldev.fullpvp.event.clan.ClanMemberJoinEvent;
import me.pixeldev.fullpvp.event.clan.ClanMemberQuitEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.utils.TickCause;
import me.pixeldev.fullpvp.utils.fake.ActionData;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@InjectAll
public class ClanListeners implements Listener {

    private Cache<UUID, ClanRequest> clanRequestCache;
    private Cache<UUID, ActionData> actionDataCache;
    private Cache<UUID, Clan> editMessagesCache;
    private Storage<String, Clan> clanStorage;
    private Message message;
    private ClanUtilities clanUtilities;
    private ClanMessagesFormatter clanMessagesFormatter;

    @Delegates
    private Message fileMessage;

    @Named("config")
    private FileCreator config;

    @EventHandler
    public void onServerTick(FullPVPTickEvent event) {
        if (event.getCause() != TickCause.MINUTE) {
            return;
        }

        clanRequestCache.get().forEach((uuid, clanRequest) -> clanRequest.getClanRequests().forEach((clanName, time) -> {
            int newTime = time - 1;

            clanRequest.getClanRequests().put(clanName, newTime);

            if (newTime == 0) {
                Player target = Bukkit.getPlayer(uuid);

                target.sendMessage(message.getMessage(target, "clans.request-expire-target")
                        .replace("%clan%", clanName)
                );

                clanStorage.find(clanName).ifPresent(clan -> {
                    Player sender = Bukkit.getPlayer(clan.getCreator());

                    sender.sendMessage(message.getMessage(sender, "clans.request-expire-sender")
                            .replace("%target%", target.getName())
                    );
                });

                for (Map.Entry<UUID, ActionData> entry : actionDataCache.get().entrySet()) {
                    if (entry.getValue().getPlayerId().equals(target.getUniqueId())) {
                        actionDataCache.remove(entry.getKey());

                        break;
                    }
                }

                clanRequest.getClanRequests().remove(clanName);
            }
        }));
    }

    @EventHandler
    public void onMemberJoin(ClanMemberJoinEvent event) {
        Player player = event.getPlayer();
        Clan clan = event.getClan();

        String joinMessage = fileMessage.getMessage(null, "clans.member-join-server")
                .replace("%member%", player.getName());

        Player creator = Bukkit.getPlayer(clan.getCreator());

        if (creator != null) {
            creator.sendMessage(joinMessage);
        }

        clanUtilities.sendMessageToMembers(clan, joinMessage);

        List<String> messages = clan.getProperties().getMessages();

        messages.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

        clanMessagesFormatter.getCenteredMessages(messages).forEach(player::sendMessage);
    }

    @EventHandler
    public void onMemberLeave(ClanMemberQuitEvent event) {
        Player player = event.getPlayer();
        Clan clan = event.getClan();

        String leaveMessage = fileMessage.getMessage(null, "clans.member-leave-server")
                .replace("%member%", player.getName());

        Player creator = Bukkit.getPlayer(clan.getCreator());

        if (creator != null) {
            creator.sendMessage(leaveMessage);
        }

        clanUtilities.sendMessageToMembers(clan, leaveMessage);
    }

    @EventHandler
    public void onClanChat(ClanChatEvent event) {
        Player player = event.getPlayer();
        Clan clan = event.getClan();
        String message = event.getMessage();

        String finalMessage = config.getString("clans.chat-format")
                .replace("%player%", player.getName())
                .replace("%message%", message);

        Bukkit.getPlayer(clan.getCreator()).sendMessage(finalMessage);
        clanUtilities.sendMessageToMembers(clan, finalMessage);
    }

    @EventHandler
    public void onEditMessage(ClanEditMessagesEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        Clan clan = event.getClan();

        if (message.equalsIgnoreCase("cancel")) {
            player.sendMessage(this.message.getMessage(player, "clans.edit-messages.cancel"));
            editMessagesCache.remove(player.getUniqueId());

            return;
        }

        List<String> messages = clan.getProperties().getMessages();

        if (message.equals("(empty)")) {
            messages.add("");
        } else {
            messages.add(message);
        }

        messages.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

        player.sendMessage("");

        player.sendMessage(this.message.getMessage(player, "clans.edit-messages.successfully-add")
                .replace("%message%", String.join("\n", clanMessagesFormatter.getCenteredMessages(messages)))
        );
    }


}