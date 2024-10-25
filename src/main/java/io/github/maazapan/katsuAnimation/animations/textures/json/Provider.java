package io.github.maazapan.katsuAnimation.animations.textures.json;

import java.util.List;

public class Provider {
    private String type;
    private String file;
    private Integer ascent;
    private Integer height;
    private List<String> chars;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getAscent() {
        return ascent;
    }

    public void setAscent(Integer ascent) {
        this.ascent = ascent;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getChars() {
        return chars;
    }

    public void setChars(List<String> chars) {
        this.chars = chars;
    }
}
