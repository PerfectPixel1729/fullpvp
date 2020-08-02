package me.pixeldev.fullpvp.chest.viewer;

import me.pixeldev.fullpvp.chest.SupplierChest;

import java.util.concurrent.ConcurrentHashMap;

public interface UserViewer {

    ConcurrentHashMap<SupplierChest, Integer> getViewed();

}