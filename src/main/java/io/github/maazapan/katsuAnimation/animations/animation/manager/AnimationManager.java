package io.github.maazapan.katsuAnimation.animations.animation.manager;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.Animation;
import io.github.maazapan.katsuAnimation.animations.animation.PlayerAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.type.AnimationType;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;
import io.github.maazapan.katsuAnimation.utils.KatsuUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class AnimationManager {

    private final KatsuAnimation plugin;

    private final Map<UUID, PlayerAnimation> playerAnimationMap = new HashMap<>();
    private final List<Animation> animations = new ArrayList<>();

    public AnimationManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    /**
     * Play animation by player and name
     *
     * @param player player
     * @param name   animation name
     */
    public void play(Player player, String name, AnimationType animationType, boolean repeat, int updateTick) {
        Animation animation = getAnimation(name);
        if (animation == null) return;

        PlayerAnimation playerAnimation = new PlayerAnimation(player.getUniqueId(), animation);
        playerAnimation.setType(animationType);
        playerAnimation.setRepeat(repeat);
        playerAnimation.setUpdateTick(updateTick);

        playerAnimationMap.put(player.getUniqueId(), playerAnimation);
    }

    /**
     * Finish animation by UUID
     *
     * @param uuid player UUID
     */
    public void finish(UUID uuid) {
        playerAnimationMap.remove(uuid);
    }


    public void deleteAnimation(String name) {
        animations.remove(getAnimation(name));

        File defaultJson = new File(plugin.getDataFolder() + TexturesManager.DEFAULT_JSON_PATH + name + ".json");
        File textureFolder = new File(plugin.getDataFolder() + TexturesManager.TEXTURES_PATH + name);

        KatsuUtils.deleteFolder(textureFolder);
        defaultJson.delete();
    }

    /**
     * Get animation by name
     *
     * @param name animation name
     * @return animation
     */
    public Animation getAnimation(String name) {
        return animations.stream().filter(animation -> animation.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if player has active animation
     *
     * @param uuid player UUID
     * @return true if player has active animation
     */
    public boolean isActiveAnimation(UUID uuid) {
        return playerAnimationMap.containsKey(uuid);
    }

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public PlayerAnimation getPlayerAnimation(UUID uuid) {
        return playerAnimationMap.get(uuid);
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public Map<UUID, PlayerAnimation> getPlayerAnimations() {
        return playerAnimationMap;
    }
}
