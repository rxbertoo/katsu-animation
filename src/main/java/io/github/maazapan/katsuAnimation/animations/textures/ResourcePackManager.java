package io.github.maazapan.katsuAnimation.animations.textures;

import io.github.maazapan.katsuAnimation.exceptions.InvalidHashException;
import io.github.maazapan.katsuAnimation.exceptions.ResourcePackApplyException;
import io.github.maazapan.katsuAnimation.exceptions.ResourcePackNotFound;
import io.github.maazapan.katsuAnimation.utils.HashUtil;
import org.bukkit.entity.Player;

import java.io.File;

public class ResourcePackManager {

    public static void applyResourcePack(Player player, String url) {
        File resourcePack = new File("plugins/KatsuAnimation/resourcepack.zip");

        if (!resourcePack.exists()) {
            throw new ResourcePackNotFound("The resource pack file was not found, please check the file path.");
        }

        byte[] hash = HashUtil.generateSHA1(resourcePack);

        if (hash == null) {
            throw new InvalidHashException("The hash of the resource pack could not be generated.");
        }

        try {
            player.setResourcePack(url, hash);

        } catch (Exception e) {
            throw new ResourcePackApplyException("An error occurred while applying the resource pack.");
        }
    }
}
