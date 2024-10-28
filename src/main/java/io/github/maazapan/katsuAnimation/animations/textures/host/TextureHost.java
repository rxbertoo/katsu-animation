package io.github.maazapan.katsuAnimation.animations.textures.host;

import com.sun.net.httpserver.HttpServer;
import io.github.maazapan.katsuAnimation.KatsuAnimation;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class TextureHost {

    private final KatsuAnimation plugin;
    public static String TEXTURE_PACK_URL = null;

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

            URL url = new URL("http://checkip.amazonaws.com/");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String ip = br.readLine();
                TEXTURE_PACK_URL = "http://" + ip + ":" + port + "/texturepack";

                if (!isRunning(ip, port)) {
                    plugin.getLogger().warning("Failed to start texture pack host server. Port " + port + " is already in used or is blocked.");
                    return;
                }
                plugin.getLogger().info("Texture host started on port " + port + " url " + TEXTURE_PACK_URL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRunning(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
