package me.fakepumpkin7.pumpkinframework.gui.book.util;

import org.bukkit.ChatColor;

import java.util.EnumMap;
import java.util.Map;

public class BookBuilder {

    private final StringBuilder bookBuilder = new StringBuilder("{pages:[\"[");
    private StringBuilder pageBuilder = new StringBuilder();
    private final Map<TextFormat, String> pendingFormattingChanges = new EnumMap<>(TextFormat.class);

    public BookBuilder addText(String text) {
        applyFormatting();

        if (pageBuilder != null) {
            bookBuilder.append(pageBuilder);
        }

        if (pageBuilder != null) {
            if (pageBuilder.length() > 0) {
                pageBuilder = new StringBuilder(",");
            } else if (bookBuilder.length() == 10) {
                pageBuilder = new StringBuilder("\\\"\\\",");
            }
        } else {
            pageBuilder = new StringBuilder(",\"[\\\"\\\",");
        }

        pageBuilder.append("{\\\"text\\\":\\\"").append(text).append("\\\"");

        format(TextFormat.COLOR, ChatColor.BLACK);

        return this;
    }

    public BookBuilder format(ChatColor chatColor) {
        return format(TextFormat.COLOR, chatColor);
    }

    public BookBuilder format(TextFormat formatType) {
        return format(formatType, true);
    }

    public BookBuilder format(TextFormat formatType, boolean enabled) {
        return format(formatType, Boolean.valueOf(enabled).toString().toLowerCase());
    }

    public BookBuilder format(TextFormat formatType, ChatColor chatColor) {
        return format(formatType, chatColor.getChar());
    }

    public BookBuilder format(TextFormat formatType, char colour) {
        return format(formatType, getStringFromColour(colour));
    }

    public BookBuilder format(TextFormat formatType, String text) {
        pendingFormattingChanges.put(formatType, text);

        return this;
    }

    public BookBuilder applyFormatting() {
        if (!pendingFormattingChanges.isEmpty()) {
            for (Map.Entry<TextFormat, String> entry : pendingFormattingChanges.entrySet()) {
                pageBuilder.append(",");

                if (entry.getKey().hasTranslation()) {
                    pageBuilder.append("\\\"").append(entry.getKey().getJsonTranslation()).append("\\\":");
                    if (entry.getValue().equals("true") || entry.getValue().equals("false")) {
                        pageBuilder.append(entry.getValue());
                    } else {
                        pageBuilder.append("\\\"").append(entry.getValue()).append("\\\"");
                    }
                } else {
                    switch (entry.getKey()) {
                        case OPEN_URL: {
                            pageBuilder.append("\\\"clickEvent\\\":{\\\"action\\\":\\\"open_url\\\",\\\"value\\\":\\\"").append(entry.getValue()).append("\\\"}");
                            break;
                        }
                        case HOVER_TEXT: {
                            pageBuilder.append("\\\"hoverEvent\\\":{\\\"action\\\":\\\"show_text\\\",\\\"value\\\":\\\"").append(entry.getValue()).append("\\\"}");
                            break;
                        }
                        case RUN_COMMAND: {
                            pageBuilder.append("\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"").append(entry.getValue()).append("\\\"}");
                            break;
                        }
                        case CHANGE_PAGE: {
                            pageBuilder.append("\\\"clickEvent\\\":{\\\"action\\\":\\\"change_page\\\",\\\"value\\\":\\\"").append(entry.getValue()).append("\\\"}");
                            break;
                        }
                    }
                }
            }

            pageBuilder.append("}");

            pendingFormattingChanges.clear();
        }

        return this;
    }

    public BookBuilder addPage() {
        applyFormatting();

        bookBuilder.append(pageBuilder.append("]\""));

        pageBuilder = null;

        return this;
    }

    public BookBuilder addFinalPage() {
        applyFormatting();

        bookBuilder.append(pageBuilder.append("]\"]"));

        pageBuilder = null;

        return this;
    }

    public String build() {
        applyFormatting();

        return addFinalPage().bookBuilder.append(",title:\"\",author:\"\"}").toString();
    }

    public String getCurrentPageContents() {
        String[] split = (bookBuilder.toString() + pageBuilder.toString()).split("(]\\\")");

        return split[split.length - 1];
    }

    public static String getStringFromColour(char colour) {
        switch (colour) {
            case '0':
                return "black";
            case '1':
                return "dark_blue";
            case '2':
                return "dark_green";
            case '3':
                return "dark_aqua";
            case '4':
                return "dark_red";
            case '5':
                return "dark_purple";
            case '6':
                return "gold";
            case '7':
                return "gray";
            case '8':
                return "dark_gray";
            case '9':
                return "blue";

            case 'a':
                return "green";
            case 'b':
                return "aqua";
            case 'c':
                return "red";
            case 'd':
                return "light_purple";
            case 'e':
                return "yellow";
            case 'f':
                return "white";
        }

        return "black";
    }
}
