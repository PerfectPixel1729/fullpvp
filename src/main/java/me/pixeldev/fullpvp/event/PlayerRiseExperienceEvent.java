package me.pixeldev.fullpvp.event;

import me.pixeldev.fullpvp.user.User;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRiseExperienceEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final User user;
    private final int after;

    public PlayerRiseExperienceEvent(Player who, User user, int after) {
        super(who);
        this.user = user;
        this.after = after;
    }

    public User getUser() {
        return user;
    }

    public int getAfter() {
        return after;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}