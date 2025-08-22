package io.github.maazapan.katsuAnimation.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

public class DownloadGif {


    public static String downloadFromURL(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);

        String fileName = Paths.get(url.getPath()).getFileName().toString();
        InputStream in = url.openStream();

        FileOutputStream out = new FileOutputStream("plugins/KatsuAnimation/gifs/" +fileName);

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.close();
        return fileName;
    }

    public static boolean isURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
