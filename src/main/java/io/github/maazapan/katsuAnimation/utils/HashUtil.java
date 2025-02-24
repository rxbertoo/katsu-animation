package io.github.maazapan.katsuAnimation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashUtil {

    public static byte[] generateSHA1(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            try (InputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            return digest.digest();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}