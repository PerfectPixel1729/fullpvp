package me.perfectpixel.fullpvp.listeners.clan;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.request.ClanRequest;
import me.perfectpixel.fullpvp.event.FullPVPTickEvent;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.utils.TickCause;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ClanListeners implements Listener {

    @Inject
    private Cache<UUID, ClanRequest> clanRequestCache;

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Message message;

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

                clanRequest.getClanRequests().remove(clanName);
            }
        }));
    }

}