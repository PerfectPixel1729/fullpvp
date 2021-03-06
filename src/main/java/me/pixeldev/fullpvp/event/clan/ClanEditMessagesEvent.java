package me.pixeldev.fullpvp.event.clan;

import me.pixeldev.fullpvp.clans.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ClanEditMessagesEvent extends ClanEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String message;

    public ClanEditMessagesEvent(Player who, Clan clan, String message) {
        super(who, clan);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}