package com.craft404.maximus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GROUP_MEMBERS")
//,foreignKeys = {@ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "groupId"),@ForeignKey(entity = People.class, parentColumns = "id", childColumns = "personId")}
public class GroupMember {

    @PrimaryKey(autoGenerate = true)
    public long id;
    private long groupId;
    private long personId;

    public GroupMember(long groupId, long personId) {
        this.groupId = groupId;
        this.personId = personId;
    }

    public long getId() {
        return id;
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
}
