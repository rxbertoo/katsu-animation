package io.github.maazapan.katsuAnimation.animations.animation;

import io.github.maazapan.katsuAnimation.animations.animation.type.AnimationType;

import java.util.UUID;

public class PlayerAnimation {

    private final UUID uuid;

    private final Animation animation;
    private AnimationType type;

    private int character;
    private boolean repeat;

    private int updateTick;
    private long time;

    public PlayerAnimation(UUID uuid, Animation animation) {
        this.character = animation.getCharacter();
        this.type = AnimationType.ACTION_BAR;
        this.animation = animation;
        this.uuid = uuid;
        this.repeat = true;
        this.updateTick = 0;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Animation getAnimation() {
        return animation;
    }

    public AnimationType getType() {
        return type;
    }

    public void setType(AnimationType type) {
        this.type = type;
    }

    public int getCharacter() {
        return character;
    }

    public int getUpdateTick() {
        return updateTick;
    }

    public void setUpdateTick(int updateTick) {
        this.updateTick = updateTick;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void updateTime() {
        this.time = System.currentTimeMillis() + (updateTick * 20L);
    }

    public void nextCharacter() {
        if (character >= animation.getCharacterMax()) {
            character = repeat ? animation.getCharacter() : 0;
            return;
        }
        character++;
    }
}
