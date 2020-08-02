package me.pixeldev.fullpvp.event.clan;

import me.pixeldev.fullpvp.clans.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ClanMemberQuitEvent extends ClanEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ClanMemberQuitEvent(Player who, Clan clan) {
        super(who, clan);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}