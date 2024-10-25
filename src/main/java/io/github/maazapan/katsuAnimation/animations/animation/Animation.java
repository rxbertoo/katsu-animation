package io.github.maazapan.katsuAnimation.animations.animation;

import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;

public class Animation {

    private int characterMax;

    private final String font;
    private final String name;

    public Animation(String name) {
        this.font = "minecraft:" + name;
        this.name = name;
    }

    public void setCharacterMax(int characterMax) {
        this.characterMax = characterMax;
    }

    public int getCharacterMax() {
        return characterMax;
    }

    public String getFont() {
        return font;
    }

    public String getName() {
        return name;
    }

    public int getCharacter() {
        return TexturesManager.MAIN_CHARACTER;
    }
}
