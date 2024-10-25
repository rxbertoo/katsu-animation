package io.github.maazapan.katsuAnimation.animations.files;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class GifManager {

    private final KatsuAnimation plugin;

    public GifManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    /**
     * Create a GIF file, and save each frame as a PNG file.
     * at the textures' folder.
     *
     * @param fileName The name of the file.
     */
    public void convertGifToPng(String fileName, Runnable callback) {
        try {
            File file = new File(plugin.getDataFolder() + "/gifs/" + fileName);
            String name = fileName.substring(0, fileName.lastIndexOf('.'));

            ImageInputStream inputStream = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
            ImageReader reader = readers.next();

            reader.setInput(inputStream);

            Files.createDirectories(Paths.get(plugin.getDataFolder() + TexturesManager.TEXTURES_PATH + "/" + name));

            for (int i = 0; i < reader.getNumImages(true); i++) {
                BufferedImage frame = resizeImage(reader.read(i));

                File outputfile = new File(plugin.getDataFolder() + TexturesManager.TEXTURES_PATH + "/" + name + "/" + i + ".png");
                ImageIO.write(frame, "png", outputfile);
            }

            callback.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resize an image.
     *
     * @param originalImage The original image.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(255, 255, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        float scale = (float) width / height;

        if (width > height) {
            width = 255;
            height = (int) (255 / scale);
        } else {
            height = 255;
            width = (int) (255 * scale);
        }

        int x = (255 - width) / 2;
        int y = (255 - height) / 2;

        g.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), x, y, null);
        g.dispose();

        return resizedImage;
    }
}
