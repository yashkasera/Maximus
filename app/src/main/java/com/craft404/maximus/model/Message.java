package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MESSAGES")
public class Message {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String message;
    private long groupId;
    private long personId;
    private String timestamp;

    public Message(String message, long groupId, long personId, String timestamp) {
        this.message = message;
        this.groupId = groupId;
        this.personId = personId;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
