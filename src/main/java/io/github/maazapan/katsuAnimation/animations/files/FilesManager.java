package io.github.maazapan.katsuAnimation.animations.files;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesManager {

    private final KatsuAnimation plugin;

    public FilesManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        try {
            plugin.saveDefaultConfig();

            // Create the resourcepack folder if it doesn't exist.
            if (!Files.exists(Paths.get(plugin.getDataFolder() + "/resourcepack"))) {
                String[] names = {
                        "pack.png",
                        "pack.mcmeta",
                };

                Files.createDirectories(Paths.get(plugin.getDataFolder() + TexturesManager.TEXTURES_PATH));
                Files.createDirectories(Paths.get(plugin.getDataFolder() + TexturesManager.DEFAULT_JSON_PATH));

                for (String name : names) {
                    if (!Files.exists(Paths.get(plugin.getDataFolder() + "/resourcepack/" + name))) {
                        plugin.saveResource("resourcepack/" + name, false);
                    }
                }
            }

            // Create the data folder if it doesn't exist.
            if (!Files.exists(Paths.get(plugin.getDataFolder() + "/gifs"))) {
                TexturesManager texturesManager = new TexturesManager(plugin);

                Files.createDirectory(Paths.get(plugin.getDataFolder() + "/gifs"));
                plugin.saveResource("gifs/countdown.gif", false);

                texturesManager.createTexture("countdown.gif", 0, 0,20);
                plugin.getLogger().info("Success created countdown default animation...");
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
