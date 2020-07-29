package me.perfectpixel.fullpvp.clans.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultClanRequest implements ClanRequest {

    private final Map<String, Integer> clanRequests = new ConcurrentHashMap<>();

    @Override
    public Map<String, Integer> getClanRequests() {
        return clanRequests;
    }

}