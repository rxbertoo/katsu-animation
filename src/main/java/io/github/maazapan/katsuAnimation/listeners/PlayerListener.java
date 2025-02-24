package io.github.maazapan.katsuAnimation.listeners;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationManager;
import io.github.maazapan.katsuAnimation.animations.textures.ResourcePackManager;
import io.github.maazapan.katsuAnimation.animations.textures.host.TextureHost;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
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

    /**
     * When player join set the texture pack.
     *
     * @param event PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player player = event.getPlayer();

        if (config.getBoolean("config.texture-pack-join.enable")) {
            try {
                String url = config.getString("config.texture-pack-join.url");

                if (Objects.requireNonNull(url).equalsIgnoreCase("self-host")) {
                    if (TextureHost.TEXTURE_PACK_URL == null) {
                        plugin.getLogger().warning("The texture pack self host is not enabled, the texture pack will not be downloaded.");
                        plugin.getLogger().warning("you can disable self-host or use external host and put link in the config.");
                        return;
                    }
                    url = TextureHost.TEXTURE_PACK_URL;
                }

                ResourcePackManager.applyResourcePack(player, url);

            } catch (Exception e) {
                plugin.getLogger().warning("An error occurred while downloading the texture pack.");
                plugin.getLogger().warning("Make sure the url in the config is correct or texture is compiled.");
            }
        }
    }
}
