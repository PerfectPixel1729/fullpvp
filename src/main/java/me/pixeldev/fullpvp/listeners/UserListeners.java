package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.event.PlayerRiseExperienceEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.packets.TitleMessenger;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

@InjectAll
public class UserListeners implements Listener {

    @Named("config")
    private FileCreator config;

    private Storage<Integer, Kit> kitStorage;
    private Message message;
    private TitleMessenger titleMessenger;

    @EventHandler
    public void onRiseExperience(PlayerRiseExperienceEvent event) {
        Player player = event.getPlayer();
        User user = event.getUser();
        int after = event.getAfter();

        int required = config.getInt("game.addition-experience");

        if (after >= user.getExperience().getTo()) {
            int to = user.getExperience().getTo() + required;

            if (kitStorage.find(to).isPresent()) {
                user.setKitLevel(to);
            }

            user.getLevel().add(1);
            user.getExperience().getCurrent().set(0);
            user.getExperience().setTo(to);

            player.sendMessage(message.getMessage(player, "user.level-up"));
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
            titleMessenger.sendTitle(player, message.getMessage(player, "user.level-up-title"), 20, 60, 20);
        }
    }

}