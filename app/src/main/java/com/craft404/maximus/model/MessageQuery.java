package com.craft404.maximus.model;

public class MessageQuery {
    long id;
    String message;
    String personName;
    String groupName;
    boolean elite;
    boolean spammer;
    String timestamp;

    public MessageQuery(long id, String message, String personName, String groupName, boolean elite, boolean spammer, String timestamp) {
        this.id = id;
        this.message = message;
        this.personName = personName;
        this.groupName = groupName;
        this.elite = elite;
        this.spammer = spammer;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getPersonName() {
        return personName;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isElite() {
        return elite;
    }

    public boolean isSpammer() {
        return spammer;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
