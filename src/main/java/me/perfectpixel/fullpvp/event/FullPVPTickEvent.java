package me.perfectpixel.fullpvp.event;

import lombok.Getter;

import me.perfectpixel.fullpvp.utils.TickCause;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
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

}