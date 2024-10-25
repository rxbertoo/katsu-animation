package io.github.maazapan.katsuAnimation.utils;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    ZipOutputStream output;
    Path basePath;

    public Zipper(File zip) throws FileNotFoundException {
        this.output = new ZipOutputStream(new FileOutputStream(zip));
    }


    public void setBasePath(File basePath) {
        this.basePath = basePath.toPath();
    }

    private boolean zipFile(File file) {
        try {
            byte[] buf = new byte[1024];
            // Get the relative path instead of the full path
            String entryName = basePath.relativize(file.toPath()).toString().replace("\\", "/");
            output.putNextEntry(new ZipEntry(entryName));
            FileInputStream fis = new FileInputStream(file);
            int len;
            while ((len = fis.read(buf)) > 0) {
                output.write(buf, 0, len);
            }
            fis.close();
            output.closeEntry();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean zipDir(File dir) {
        try {
            String entryName = basePath.relativize(dir.toPath()).toString().replace("\\", "/") + "/";
            output.putNextEntry(new ZipEntry(entryName));
            output.closeEntry();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean add(File... files) {
        for (File file : files) {
            if (file.isDirectory()) {
                zipDir(file);
                add(file.listFiles());
            } else {
                zipFile(file);
            }
        }
        return true;
    }

    public void zip(File... files) throws IOException {
        add(files);
        output.finish();
        output.close();
    }
}
