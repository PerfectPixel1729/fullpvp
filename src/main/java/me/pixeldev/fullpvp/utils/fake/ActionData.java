package me.pixeldev.fullpvp.utils.fake;

import java.util.UUID;

/**
 * Class holding information about an action
 */
public class ActionData {

    /**
     * The player to execute the action on
     */
    private final UUID playerId;

    /**
     * The action to execute
     */
    private final EasyTextComponent.PlayerAction action;

    /**
     * Whether the action should expire
     */
    private final boolean expire;

    /**
     * ActionData constructor
     *
     * @param playerId The {@link UUID} of the player to execute the action on
     * @param action   The {@link EasyTextComponent.PlayerAction} to execute
     */
    public ActionData(UUID playerId, EasyTextComponent.PlayerAction action, boolean expire) {
        this.playerId = playerId;
        this.action = action;
        this.expire = expire;
    }

    /**
     * Gets the UUID of the player to execute the action upon
     *
     * @return The stored {@link UUID}
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Gets the action associated with this ActionData object
     *
     * @return The stored {@link EasyTextComponent.PlayerAction}
     */
    public EasyTextComponent.PlayerAction getAction() {
        return action;
    }

    /**
     * Whether the action should expire after being used once
     *
     * @return Whether the action should expire
     */
    public boolean shouldExpire() {
        return expire;
    }

}