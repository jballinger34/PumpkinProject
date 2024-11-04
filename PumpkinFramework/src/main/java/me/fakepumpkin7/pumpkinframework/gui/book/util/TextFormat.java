package me.fakepumpkin7.pumpkinframework.gui.book.util;

public enum TextFormat {

    OBFUSCATED("obfuscated"),
    BOLD("bold"),
    STRIKETHROUGH("strikethrough"),
    UNDERLINE("underlined"),
    ITALIC("italic"),
    COLOR("color"),

    OPEN_URL,
    HOVER_TEXT,
    RUN_COMMAND,
    CHANGE_PAGE;

    private final String jsonTranslation;

    TextFormat(String jsonTranslation) {
        this.jsonTranslation = jsonTranslation;
    }

    TextFormat() {
        this(null);
    }

    public String getJsonTranslation() {
        return this.jsonTranslation;
    }

    public boolean hasTranslation() {
        return this.jsonTranslation != null;
    }

}
