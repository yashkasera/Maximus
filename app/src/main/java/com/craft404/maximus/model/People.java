package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PEOPLE")
public class People {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String name;
    private boolean elite;
    private boolean spammer;
    private int icon;

    public People(String name, boolean elite, boolean spammer, int icon) {
        this.name = name;
        this.elite = elite;
        this.spammer = spammer;
        this.icon = icon;
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

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public boolean isSpammer() {
        return spammer;
    }

    public void setSpammer(boolean spammer) {
        this.spammer = spammer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
