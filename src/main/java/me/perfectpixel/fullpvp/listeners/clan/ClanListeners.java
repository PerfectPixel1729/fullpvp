package me.perfectpixel.fullpvp.listeners.clan;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.ClanUtilities;
import me.perfectpixel.fullpvp.clans.request.ClanRequest;
import me.perfectpixel.fullpvp.event.FullPVPTickEvent;
import me.perfectpixel.fullpvp.event.clan.ClanChatEvent;
import me.perfectpixel.fullpvp.event.clan.ClanMemberJoinEvent;
import me.perfectpixel.fullpvp.event.clan.ClanMemberQuitEvent;
import me.perfectpixel.fullpvp.files.FileCreator;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.utils.TickCause;
import me.perfectpixel.fullpvp.utils.fake.ActionData;

import me.yushust.inject.Inject;

import me.yushust.inject.name.Named;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClanListeners implements Listener {

    @Inject
    private Cache<UUID, ClanRequest> clanRequestCache;

    @Inject
    private Cache<UUID, ActionData> actionDataCache;

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Message message;

    @Inject
    private ClanUtilities clanUtilities;

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
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

        List<String> messages = clan.getProperties().getMessages();

        messages.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

        messages.forEach(player::sendMessage);

        String joinMessage = fileMessage.getMessage(null, "clans.member-join-server")
                .replace("%member%", player.getName());

        Bukkit.getPlayer(clan.getCreator()).sendMessage(joinMessage);

        clanUtilities.sendMessageToMembers(clan, joinMessage);
    }

    @EventHandler
    public void onMemberLeave(ClanMemberQuitEvent event) {
        Player player = event.getPlayer();
        Clan clan = event.getClan();

        String leaveMessage = fileMessage.getMessage(null, "clans.member-leave-server")
                .replace("%member%", player.getName());

        Bukkit.getPlayer(clan.getCreator()).sendMessage(leaveMessage);

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

}