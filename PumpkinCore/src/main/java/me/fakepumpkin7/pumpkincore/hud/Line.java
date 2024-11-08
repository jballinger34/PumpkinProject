package me.fakepumpkin7.pumpkincore.hud;

import lombok.Getter;

@Getter
public class Line {

    private final String message;

    private final String teamKey;
    private final String prefix;
    private final String suffix;
    private final int priority;

    public Line(int priority, String teamKey, String prefix, String suffix){
        this.priority = priority;
        this.teamKey = teamKey;
        this.prefix = prefix;
        this.suffix = suffix;
        this.message = null;
    }
    public Line(int priority,String message){
        this.priority = priority;
        this.message = message;
        this.teamKey = null;
        this.prefix = null;
        this.suffix = null;
    }

    public boolean isUpdating() {
        return !(this.teamKey == null);
    }
}
