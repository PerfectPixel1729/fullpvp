package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.menus.Menu;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class PlayerInteractListener implements Listener {

    @Inject
    @Named("chests-creators")
    private Storage<UserCreator, UUID> userCreatorStorage;

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        userCreatorStorage.find(player.getUniqueId()).ifPresent(userCreator -> userCreator.setChestLocation(block.getLocation()));
    }

}