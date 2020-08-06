package me.pixeldev.fullpvp.clans;

import me.pixeldev.fullpvp.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClanEditMessagesCache implements Cache<UUID, Clan> {

    private final Map<UUID, Clan> editMessages = new HashMap<>();

    @Override
    public Map<UUID, Clan> get() {
        return editMessages;
    }

}