package io.github.maazapan.katsuAnimation.animations.textures.host;

import com.sun.net.httpserver.HttpServer;
import io.github.maazapan.katsuAnimation.KatsuAnimation;
import org.bukkit.configuration.file.FileConfiguration;

import java.net.InetSocketAddress;

public class TextureHost {

    private final KatsuAnimation plugin;

    public TextureHost(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    public void start() {
        FileConfiguration config = plugin.getConfig();

        if (!config.getBoolean("config.texture-pack-host.enabled")) return;
        try {
            int port = config.getInt("config.texture-pack-host.port");

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/texturepack", new TexturePackHandler(plugin));
            server.setExecutor(null);
            server.start();

            plugin.getLogger().info("Texture host started on port " + port + " url http://localhost:" + port + "/texturepack");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
