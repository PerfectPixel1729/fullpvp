package me.pixeldev.fullpvp.event.clan;

import me.pixeldev.fullpvp.clans.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Clan clan;

    public ClanEvent(Player who, Clan clan) {
        super(who);

        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}