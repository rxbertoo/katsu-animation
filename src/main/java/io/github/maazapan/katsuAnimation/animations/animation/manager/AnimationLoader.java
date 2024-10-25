package io.github.maazapan.katsuAnimation.animations.animation.manager;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.Animation;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;

import java.io.File;

public class AnimationLoader {

    private final KatsuAnimation plugin;

    public AnimationLoader(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    public void load() {
        AnimationManager animationManager = plugin.getAnimationManager();

        if (!animationManager.getAnimations().isEmpty()) {
            animationManager.getAnimations().clear();
        }
        try {
            File textureDirectory = new File(plugin.getDataFolder() + TexturesManager.TEXTURES_PATH);

            if (!textureDirectory.exists()) return;
            for (File textureFolder : textureDirectory.listFiles()) {
                int size = textureFolder.listFiles().length - 1;
                int characterMax = TexturesManager.MAIN_CHARACTER;

                for (int i = 0; i < size; i++) {
                    characterMax++;
                }
                Animation animation = new Animation(textureFolder.getName());
                animation.setCharacterMax(characterMax);

                animationManager.addAnimation(animation);
            }
            plugin.getLogger().info("Success loaded " + animationManager.getAnimations().size() + " animations.");

        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load animations.");
            e.printStackTrace();
        }
    }
}
