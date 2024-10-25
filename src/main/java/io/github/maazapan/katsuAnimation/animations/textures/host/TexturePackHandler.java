package io.github.maazapan.katsuAnimation.animations.textures.host;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.maazapan.katsuAnimation.KatsuAnimation;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TexturePackHandler implements HttpHandler {

    private final KatsuAnimation plugin;

    public TexturePackHandler(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        File file = new File(plugin.getDataFolder() + "/resourcepack.zip");

        if (!file.exists()) return;

        byte[] fileBytes = Files.readAllBytes(Paths.get(file.getPath()));

        exchange.getResponseHeaders().set("Content-Type", "application/zip");
        exchange.sendResponseHeaders(200, fileBytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(fileBytes);
        os.close();
    }
}
