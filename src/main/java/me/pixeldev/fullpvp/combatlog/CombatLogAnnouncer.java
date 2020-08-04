package me.pixeldev.fullpvp.combatlog;

import org.bukkit.entity.Player;

public interface CombatLogAnnouncer {

    void sendPlayerDisconnect(Player player);

    void sendPlayerLog(Player player);

    void sendPlayerFinishLog(Player player);

}