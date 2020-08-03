package me.pixeldev.fullpvp.message;

import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.message.placeholder.PlaceholderApplier;

import team.unnamed.inject.Inject;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class SimpleMessageDecorator implements Message {

    @Inject
    @Delegates
    private Message delegate;

    @Inject
    private PlaceholderApplier placeholderApplier;

    @Override
    public String getMessage(Player player, String id) {
        return format(player, delegate.getMessage(player, id));
    }

    @Override
    public List<String> getMessages(Player player, String messageId) {
        List<String> messages = delegate.getMessages(player, messageId);
        messages.replaceAll(line -> format(player, line));

        return messages;
    }

    private String format(Player player, String text) {
        String message = ChatColor.translateAlternateColorCodes('&', text);
        message = placeholderApplier.setPlaceHolders(player, message);

        return message;
    }

}