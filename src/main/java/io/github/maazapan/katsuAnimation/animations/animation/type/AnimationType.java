package io.github.maazapan.katsuAnimation.animations.animation.type;

public enum AnimationType {

    TITLE,
    ACTION_BAR;

    public static AnimationType get(String name) {
        for (AnimationType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
