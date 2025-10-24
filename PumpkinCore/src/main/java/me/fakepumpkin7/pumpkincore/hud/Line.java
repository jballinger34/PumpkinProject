package me.fakepumpkin7.pumpkincore.hud;


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

    public String getMessage() {
        return message;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getPriority() {
        return priority;
    }
}
