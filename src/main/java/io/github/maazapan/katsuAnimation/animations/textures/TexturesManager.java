package io.github.maazapan.katsuAnimation.animations.textures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.files.GifManager;
import io.github.maazapan.katsuAnimation.animations.textures.json.ObjectDefault;
import io.github.maazapan.katsuAnimation.animations.textures.json.Provider;
import io.github.maazapan.katsuAnimation.utils.Zipper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TexturesManager {

    public static String DEFAULT_JSON_PATH = "/resourcepack/assets/minecraft/font/";
    public static String TEXTURES_PATH = "/resourcepack/assets/minecraft/textures/katsu/";

    public static int MAIN_CHARACTER = 57344;

    private final KatsuAnimation plugin;

    public TexturesManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    /**
     * Create a texture.
     * These methods convert a GIF file to PNG files.
     * and create the default JSON file to image name.
     *
     * @param name   The name of the file.
     * @param ascent The ascent of the font.
     * @param height The height of the font.
     */
    public void createTexture(String name, int skipFrames, int ascent, int height) {
        GifManager gifManager = new GifManager(plugin);
        gifManager.convertGifToPng(name, skipFrames, () -> this.createDefaultJSON(name, ascent, height));
    }

    /**
     * Compile the resource pack.
     * This method will create a ZIP file containing the resource pack.
     */
    public void compile() throws Exception {
        // Compile the resource pack.
        File resourcePackDirectory = new File(plugin.getDataFolder(), "resourcepack");

        Zipper zipper = new Zipper(new File(plugin.getDataFolder(), "resourcepack.zip"));

        zipper.setBasePath(resourcePackDirectory);
        zipper.zip(resourcePackDirectory);
    }

    /**
     * Create the default JSON file.
     * This file is used to display the animation in the game.
     */
    @SuppressWarnings("all")
    private void createDefaultJSON(String name, int ascent, int height) {
        try {
            name = name.substring(0, name.lastIndexOf('.'));
            File frameDirectory = new File(plugin.getDataFolder() + TEXTURES_PATH + "/" + name);

            if (!frameDirectory.exists()) return;
            ObjectDefault objectDefault = new ObjectDefault();

            List<Provider> providers = new ArrayList<>();
            List<File> files = new ArrayList<>(Arrays.asList(frameDirectory.listFiles()));

            this.sort(files);

            int i = TexturesManager.MAIN_CHARACTER;

            for (File frameFile : files) {
                // Create a provider for each frame.
                char character = (char) i;

                Provider provider = new Provider();
                provider.setType("bitmap");
                provider.setFile("minecraft:katsu/" + frameDirectory.getName() + "/" + frameFile.getName());
                provider.setAscent(ascent);
                provider.setHeight(height);
                provider.setChars(Arrays.asList(character + ""));
                providers.add(provider);
                i++;
            }
            objectDefault.setProviders(providers);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonContent = gson.toJson(objectDefault);

            File file = new File(plugin.getDataFolder() + "/" + DEFAULT_JSON_PATH + frameDirectory.getName() + ".json");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write(jsonContent);
            }

        } catch (Exception e) {
            plugin.getLogger().warning("An error occurred while creating default JSON file.");
            e.printStackTrace();
        }
    }

    private void sort(List<File> list) {
        list.sort((f1, f2) -> {
            int i1 = Integer.parseInt(f1.getName().substring(0, f1.getName().lastIndexOf('.')));
            int i2 = Integer.parseInt(f2.getName().substring(0, f2.getName().lastIndexOf('.')));
            return Integer.compare(i1, i2);
        });
    }
}
