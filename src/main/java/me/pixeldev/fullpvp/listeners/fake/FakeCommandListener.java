package me.pixeldev.fullpvp.listeners.fake;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.utils.fake.ActionData;

import me.yushust.inject.Inject;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class FakeCommandListener implements Listener {

    @Inject
    private Cache<UUID, ActionData> actionDataCache;

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split("/")[1];

        UUID id;

        try {
            id = UUID.fromString(command);
        } catch (IllegalArgumentException expected) {
            return;
        }

        actionDataCache.find(id).ifPresent(actionData -> {
            event.setCancelled(true);

            Player player = event.getPlayer();

            if (player.getUniqueId().equals(actionData.getPlayerId())) {
                actionData.getAction().run(player);

                if (actionData.shouldExpire()) {
                    actionDataCache.remove(id);
                }
            }
        });
    }

}