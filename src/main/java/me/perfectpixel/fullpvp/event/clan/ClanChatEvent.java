package me.perfectpixel.fullpvp.event.clan;

import me.perfectpixel.fullpvp.clans.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ClanChatEvent extends ClanEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String message;

    public ClanChatEvent(Player who, Clan clan, String message) {
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