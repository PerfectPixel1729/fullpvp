package me.pixeldev.fullpvp.combatlog;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.packets.ActionbarMessenger;

import org.bukkit.entity.Player;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.process.annotation.Singleton;

import java.util.UUID;

@Singleton
public class SimpleCombatLogAnnouncer implements CombatLogAnnouncer {

    @Inject
    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Inject
    private CombatLogMessageDecorator messageDecorator;

    @Inject
    private ActionbarMessenger actionbarMessenger;

    @Override
    public void sendPlayerDisconnect(Player player) {

    }

    @Override
    public void sendPlayerLog(Player player) {
        combatLogCache.find(player.getUniqueId()).ifPresent(time -> actionbarMessenger.sendActionbar(player, messageDecorator.format(time)));
    }

    @Override
    public void sendPlayerFinishLog(Player player) {
        actionbarMessenger.sendActionbar(player, messageDecorator.formatFinish());
    }

}