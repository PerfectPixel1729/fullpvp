package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.event.SupplierKitReceiveEvent;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import team.unnamed.inject.InjectAll;

import java.util.UUID;

@InjectAll
public class SupplierKitListeners implements Listener {

    private Storage<Integer, Kit> kitStorage;
    private Storage<UUID, User> userStorage;

    @EventHandler
    public void onReceive(SupplierKitReceiveEvent event) {
        Player player = event.getPlayer();

        player.sendMessage("Recibiiendo kit...");
    }

}