package me.perfectpixel.fullpvp.event.chest;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.message.Message;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SupplierChestEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final SupplierChest supplierChest;
    private final Message message;

    public SupplierChestEvent(Player who, SupplierChest supplierChest, Message message) {
        super(who);

        this.supplierChest = supplierChest;
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public SupplierChest getSupplierChest() {
        return supplierChest;
    }

    public Message getMessage() {
        return message;
    }

}