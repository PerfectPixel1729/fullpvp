package me.pixeldev.fullpvp.utils.fake;

import me.pixeldev.fullpvp.Cache;

import me.yushust.inject.Inject;
import me.yushust.inject.process.annotation.Singleton;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.apache.commons.lang.Validate;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

@Singleton
public class EasyTextComponent {

    @Inject
    private Plugin plugin;

    @Inject
    private Cache<UUID, ActionData> actionDataCache;

    /**
     * Sends a clickable action message to a player
     *
     * @param player The player to send the message to
     * @param msg    The message to send to the player
     * @param expire Whether the action should expire after being used once
     * @param action The action to execute when the player clicks the message
     */
    public TextComponent sendActionMessage(Player player, String msg, boolean expire, PlayerAction action) {
        return sendActionMessage(player, new TextComponent(msg), expire, action);
    }

    /**
     * Sends a clickable action message to a player
     *
     * @param player    The player to send the message to
     * @param component The text component to send to the player
     * @param expire    Whether the action should expire after being used once
     * @param action    The action to execute when the player clicks the message
     */
    public TextComponent sendActionMessage(Player player, TextComponent component, boolean expire, PlayerAction action) {
        return sendActionMessage(player, new TextComponent[]{component}, expire, action);
    }

    /**
     * Sends clickable action messages to a player
     *
     * @param player     The player to send the message to
     * @param components The text components to send to the player
     * @param expire     Whether the action should expire after being used once
     * @param action     The action to execute when the player clicks the message
     */
    public TextComponent sendActionMessage(Player player, TextComponent[] components, boolean expire, PlayerAction action) {
        Validate.notNull(player, "Player cannot be null");
        Validate.notNull(components, "Components cannot be null");
        Validate.notNull(action, "Action cannot be null");

        UUID id = UUID.randomUUID();

        actionDataCache.add(id, new ActionData(player.getUniqueId(), action, expire));

        for (BaseComponent component : components) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + id.toString()));
        }

        return components[0];
    }

    @FunctionalInterface
    public interface PlayerAction {

        void run(Player player);

    }

}