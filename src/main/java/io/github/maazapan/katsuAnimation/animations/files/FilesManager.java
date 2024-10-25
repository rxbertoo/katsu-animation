package io.github.maazapan.katsuAnimation.animations.files;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesManager {

    private final KatsuAnimation plugin;

    public FilesManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        try {
            plugin.saveDefaultConfig();

            // Create the data folder if it doesn't exist.
            if (!Files.exists(Paths.get(plugin.getDataFolder() + "/gifs"))) {
                Files.createDirectory(Paths.get(plugin.getDataFolder() + "/gifs"));
            }

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

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
