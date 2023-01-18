package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ELITE")
//, foreignKeys = @ForeignKey(entity = Message.class, parentColumns = "id", childColumns = "messageId")
public class EliteMessage {

    @PrimaryKey(autoGenerate = true)
    public long id;
    private long messageId;

    public EliteMessage(long messageId) {
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
