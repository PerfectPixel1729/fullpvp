package me.perfectpixel.fullpvp.event.chest;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.message.Message;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class SupplierChestEditEvent extends SupplierChestEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();

    public SupplierChestEditEvent(Player who, SupplierChest supplierChest, Message message) {
        super(who, supplierChest, message);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}