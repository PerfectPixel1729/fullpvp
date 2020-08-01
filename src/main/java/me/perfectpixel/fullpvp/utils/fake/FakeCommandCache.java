package me.perfectpixel.fullpvp.utils.fake;

import me.perfectpixel.fullpvp.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class FakeCommandCache implements Cache<UUID, ActionData> {

    private final Map<UUID, ActionData> actionData = new LinkedHashMap<>();

    @Override
    public Map<UUID, ActionData> get() {
        return actionData;
    }

}