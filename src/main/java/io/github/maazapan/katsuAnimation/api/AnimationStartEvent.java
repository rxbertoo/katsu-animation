package io.github.maazapan.katsuAnimation.api;

import io.github.maazapan.katsuAnimation.animations.animation.PlayerAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AnimationStartEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final PlayerAnimation playerAnimation;
    private final Player player;

    private boolean cancelled;

    public AnimationStartEvent(PlayerAnimation playerAnimation, Player player) {
        this.playerAnimation = playerAnimation;
        this.player = player;
        this.cancelled = false;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public Player getPlayer() {
        return player;
    }

    @NotNull
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
