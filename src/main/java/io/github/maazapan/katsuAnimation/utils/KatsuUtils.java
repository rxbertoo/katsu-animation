package io.github.maazapan.katsuAnimation.utils;

import org.bukkit.ChatColor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KatsuUtils {

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    public static String formatGifName(String name) {
        return name.replace(".gif", "");
    }

    public static boolean isBool(String message) {
        return message.equalsIgnoreCase("true") || message.equalsIgnoreCase("false");
    }

    public static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void deleteFolder(File dirFile) {
        if (dirFile.isDirectory()) {
            File[] dirs = dirFile.listFiles();
            for (File dir : dirs) {
                deleteFolder(dir);
            }
        }
        dirFile.delete();
    }
}
