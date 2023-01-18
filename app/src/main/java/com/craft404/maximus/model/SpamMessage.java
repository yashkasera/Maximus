package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SPAM")
public class SpamMessage {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private long messageId;

    public SpamMessage(long messageId) {
        this.messageId = messageId;
    }

    public long getId() {
        return id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
