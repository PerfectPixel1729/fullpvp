package me.pixeldev.fullpvp.event.chest;

import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.message.Message;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SupplierChestPreCreateEvent extends PlayerEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();

    private final UserCreator userCreator;
    private final Message message;
    private final Location location;

    public SupplierChestPreCreateEvent(Player who, UserCreator userCreator, Message message, Location location) {
        super(who);

        this.userCreator = userCreator;
        this.message = message;
        this.location = location;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Location getLocation() {
        return location;
    }

    public Message getMessage() {
        return message;
    }

    public UserCreator getUserCreator() {
        return userCreator;
    }

}