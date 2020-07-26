package me.perfectpixel.fullpvp.chest.viewer;

import me.perfectpixel.fullpvp.chest.SupplierChest;

import java.util.concurrent.ConcurrentHashMap;

public interface UserViewer {

    ConcurrentHashMap<SupplierChest, Integer> getViewed();

}