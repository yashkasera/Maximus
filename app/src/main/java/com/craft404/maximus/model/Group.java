package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GROUPS")

public class Group {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String name;
    private String last_viewed;
    private int icon;
    private boolean store_messages;
    private int priority;

    public Group(String name, String last_viewed, int icon, boolean store_messages, int priority) {
        this.name = name;
        this.last_viewed = last_viewed;
        this.icon = icon;
        this.store_messages = store_messages;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_viewed() {
        return last_viewed;
    }

    public void setLast_viewed(String last_viewed) {
        this.last_viewed = last_viewed;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isStore_messages() {
        return store_messages;
    }

    public void setStore_messages(boolean store_messages) {
        this.store_messages = store_messages;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
