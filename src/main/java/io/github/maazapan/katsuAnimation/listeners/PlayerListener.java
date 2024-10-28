package io.github.maazapan.katsuAnimation.listeners;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final KatsuAnimation plugin;

    public PlayerListener(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    /**
     * Finish the animation when the player leaves the server.
     *
     * @param event PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AnimationManager animationManager = plugin.getAnimationManager();
        FileConfiguration config = plugin.getConfig();

        UUID uuid = event.getPlayer().getUniqueId();

        if (!config.getBoolean("config.finish-animation-on-quit")) return;
        if (animationManager.isActiveAnimation(uuid)) {
            animationManager.finish(uuid);
        }
    }
}
