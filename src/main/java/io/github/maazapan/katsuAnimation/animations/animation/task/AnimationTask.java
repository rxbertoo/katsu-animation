package io.github.maazapan.katsuAnimation.animations.animation.task;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.Animation;
import io.github.maazapan.katsuAnimation.animations.animation.PlayerAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationManager;
import io.github.maazapan.katsuAnimation.utils.TitleManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class AnimationTask extends BukkitRunnable {

    private final KatsuAnimation plugin;
    private final AnimationManager animationManager;
    private final TitleManager titleManager;

    public AnimationTask(KatsuAnimation plugin) {
        this.animationManager = plugin.getAnimationManager();
        this.titleManager = new TitleManager();
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (UUID uuid : animationManager.getPlayerAnimations().keySet()) {
            PlayerAnimation playerAnimation = animationManager.getPlayerAnimation(uuid);
            Animation animation = playerAnimation.getAnimation();

            Player player = plugin.getServer().getPlayer(uuid);

            if (player == null) continue;
            if (playerAnimation.getTime() > System.currentTimeMillis()) continue;
            char character = (char) (playerAnimation.getCharacter());

            TextComponent textComponent = new TextComponent(String.valueOf(character));
            textComponent.setFont(animation.getFont());

            switch (playerAnimation.getType()) {
                case TITLE:
                    titleManager.sendTitle(player, textComponent);
                    break;
                case ACTION_BAR:
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
                    break;
            }

            playerAnimation.nextCharacter();
            playerAnimation.updateTime();

            // Finish animation
            if (playerAnimation.getCharacter() == 0) {
                animationManager.finish(player.getUniqueId());
            }
        }
    }
}
