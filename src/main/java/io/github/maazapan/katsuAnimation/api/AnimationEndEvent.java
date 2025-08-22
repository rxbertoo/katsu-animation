package io.github.maazapan.katsuAnimation.api;

import io.github.maazapan.katsuAnimation.animations.animation.PlayerAnimation;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class AnimationEndEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final PlayerAnimation playerAnimation;
    private final UUID uuid;

    private boolean cancelled;

    public AnimationEndEvent(PlayerAnimation playerAnimation, UUID uuid) {
        this.playerAnimation = playerAnimation;
        this.uuid = uuid;
        this.cancelled = false;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    /**
     * Get the player's UUID
     * may be null if the player is offline
     *
     * @return player's UUID
     */
    public UUID getUUID() {
        return uuid;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
