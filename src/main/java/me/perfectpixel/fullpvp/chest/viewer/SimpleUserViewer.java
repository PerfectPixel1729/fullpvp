package me.perfectpixel.fullpvp.chest.viewer;

import lombok.Getter;

import me.perfectpixel.fullpvp.chest.SupplierChest;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class SimpleUserViewer implements UserViewer {

    private final ConcurrentHashMap<SupplierChest, Integer> viewed = new ConcurrentHashMap<>();

}