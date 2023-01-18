package com.craft404.maximus.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MEDIA")
public class Media {
    private final String name;
    private final int type;
    private final String path;
    private final long personId;
    private final long groupId;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private boolean isDeleted;

    public Media(String name, int type, String path, long personId, long groupId, boolean isDeleted) {
        this.name = name;
        this.type = type;
        this.path = path;
        this.personId = personId;
        this.groupId = groupId;
        this.isDeleted = isDeleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public long getPersonId() {
        return personId;
    }

    public long getGroupId() {
        return groupId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
