package me.pixeldev.fullpvp.event;

import me.pixeldev.fullpvp.utils.TickCause;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FullPVPTickEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final TickCause cause;

    public FullPVPTickEvent(TickCause cause) {
        this.cause = cause;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public TickCause getCause() {
        return cause;
    }

}