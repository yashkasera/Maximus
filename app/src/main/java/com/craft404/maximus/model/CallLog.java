package com.craft404.maximus.model;

public class CallLog {
    private String number;
    private String name;
    private long duration;
    private String type;
    private long date;

    public CallLog(String number, String name, long duration, String type, long date) {
        this.number = number;
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getType() {
        return Integer.parseInt(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
