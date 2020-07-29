package me.perfectpixel.fullpvp.clans.request;

import me.perfectpixel.fullpvp.Cache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClanRequestCache implements Cache<UUID, ClanRequest> {

    private final Map<UUID, ClanRequest> clanRequests = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, ClanRequest> get() {
        return clanRequests;
    }

}