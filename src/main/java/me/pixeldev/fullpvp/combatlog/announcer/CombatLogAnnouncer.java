package me.pixeldev.fullpvp.combatlog.announcer;

import org.bukkit.entity.Player;

public interface CombatLogAnnouncer {

    void sendPlayerDisconnect(Player player);

    void sendPlayerLog(Player player);

    void sendPlayerFinishLog(Player player);

}