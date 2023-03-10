package com.craft404.maximus.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.craft404.maximus.model.EliteMessage;
import com.craft404.maximus.model.Group;
import com.craft404.maximus.model.GroupMember;
import com.craft404.maximus.model.ImportantMessage;
import com.craft404.maximus.model.Media;
import com.craft404.maximus.model.Message;
import com.craft404.maximus.model.MessageQuery;
import com.craft404.maximus.model.People;
import com.craft404.maximus.model.PinnedMessage;
import com.craft404.maximus.model.SpamMessage;

import java.util.List;

@Dao
public interface DatabaseDao {
    @Insert(entity = Group.class, onConflict = OnConflictStrategy.REPLACE)
    long insertGroup(Group group);

    @Insert(entity = People.class, onConflict = OnConflictStrategy.REPLACE)
    long insertPerson(People person);

    @Insert(entity = Message.class, onConflict = OnConflictStrategy.REPLACE)
    long insertMessage(Message message);

    @Insert(entity = Media.class, onConflict = OnConflictStrategy.REPLACE)
    void insertMedia(Media media);

    @Insert(entity = EliteMessage.class, onConflict = OnConflictStrategy.REPLACE)
    void insertElite(EliteMessage eliteMessage);

    @Insert(entity = ImportantMessage.class, onConflict = OnConflictStrategy.REPLACE)
    void insertImportant(ImportantMessage importantMessage);

    @Insert(entity = SpamMessage.class, onConflict = OnConflictStrategy.REPLACE)
    void insertSpam(SpamMessage spamMessage);

    @Insert(entity = PinnedMessage.class, onConflict = OnConflictStrategy.REPLACE)
    long insertPinned(PinnedMessage pinnedMessage);

    @Insert(entity = GroupMember.class, onConflict = OnConflictStrategy.REPLACE)
    void insertMember(GroupMember groupMember);

    @Query("SELECT * FROM GROUP_MEMBERS WHERE groupId=:groupId AND personId=:personId LIMIT 1")
    GroupMember isMember(long groupId, long personId);

    @Query("SELECT * FROM GROUPS ORDER BY priority DESC")
    LiveData<List<Group>> getGroups();

    @Query("SELECT * FROM GROUPS WHERE id=:groupId LIMIT 1")
    LiveData<Group> getGroup(long groupId);

    @Query("SELECT * FROM GROUPS WHERE NAME = :groupName LIMIT 1")
    Group getGroupByName(String groupName);

    @Query("SELECT * FROM PEOPLE WHERE NAME = :personName LIMIT 1")
    People getPersonByName(String personName);

    @Update()
    void updateGroup(Group groups);

    @Query("SELECT * FROM PINNED WHERE messageId=:messageId LIMIT 1")
    PinnedMessage isPinned(long messageId);

    @Update()
    void updatePerson(People person);

    @Query("SELECT * FROM MESSAGES")
    LiveData<List<Message>> getMessages();

    @Query("SELECT MESSAGES.id,message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "LEFT JOIN GROUPS ON MESSAGES.groupId = GROUPS.id\n" +
            "WHERE groupId = :groupId\n")
    LiveData<List<MessageQuery>> getGroupMessages(long groupId);

    @Query("SELECT MESSAGES.id,message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "LEFT JOIN ELITE ON ELITE.messageId = MESSAGES.id \n" +
            "LEFT JOIN GROUPS ON GROUPS.id = MESSAGES.groupId\n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "WHERE personId=:personId")
    LiveData<List<MessageQuery>> getPersonMessages(long personId);

    @Query("SELECT MESSAGES.id,message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "INNER JOIN ELITE ON MESSAGES.id = ELITE.messageId\n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "LEFT JOIN GROUPS ON MESSAGES.groupId = GROUPS.id\n")
    LiveData<List<MessageQuery>> getEliteMessages();

    @Query("SELECT * FROM People WHERE elite=1")
    LiveData<List<People>> getElitePeople();

    @Query("SELECT * FROM People")
    LiveData<List<People>> getPeople();

    @Query("SELECT * FROM People WHERE spammer=1")
    LiveData<List<People>> getSpammers();

    @Query("SELECT * FROM MEDIA WHERE isDeleted=1")
    LiveData<List<Media>> getDeletedMedia();

    @Query("SELECT * FROM MEDIA")
    LiveData<List<Media>> getMedia();

    @Query("SELECT * FROM MEDIA WHERE path=:path")
    LiveData<Media> getMediaByPath(String path);

    @Query("SELECT PEOPLE.id,PEOPLE.name,PEOPLE.elite,PEOPLE.spammer,PEOPLE.icon FROM GROUP_MEMBERS\n" +
            "INNER JOIN PEOPLE ON PEOPLE.id=GROUP_MEMBERS.personId\n" +
            "WHERE groupId = :groupId")
    LiveData<List<People>> getMembers(long groupId);

    @Query("SELECT * FROM MESSAGES WHERE id=:messageId")
    Message getMessage(long messageId);

    @Query("SELECT * FROM PEOPLE WHERE id=:personId")
    People getPerson(long personId);

    @Query("SELECT MESSAGES.id,message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "INNER JOIN SPAM ON MESSAGES.id = SPAM.messageId\n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "LEFT JOIN GROUPS ON MESSAGES.groupId = GROUPS.id\n")
    LiveData<List<MessageQuery>> getSpamMessages();

    @Query("SELECT MESSAGES.id,message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "INNER JOIN PINNED ON MESSAGES.id = PINNED.messageId\n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "LEFT JOIN GROUPS ON MESSAGES.groupId = GROUPS.id\n")
    LiveData<List<MessageQuery>> getPinnedMessages();

    @Query("DELETE FROM PINNED WHERE messageId=:messageId")
    int deletePinned(long messageId);

    @Query("UPDATE PEOPLE SET elite=0")
    void resetElitePeople();

    @Query("UPDATE PEOPLE SET spammer=0")
    void resetSpammers();

    @Query("SELECT MESSAGES.id, message,PEOPLE.name personName,GROUPS.name groupName,elite,spammer,timestamp FROM MESSAGES \n" +
            "INNER JOIN IMPORTANT ON MESSAGES.id = IMPORTANT.messageId\n" +
            "LEFT JOIN PEOPLE ON MESSAGES.personId = PEOPLE.id\n" +
            "LEFT JOIN GROUPS ON MESSAGES.groupId = GROUPS.id\n")
    LiveData<List<MessageQuery>> getImportantMessages();

    @Query("SELECT message FROM MESSAGES \n" +
            "LEFT JOIN GROUPS ON  GROUPS.id =  MESSAGES.groupId\n" +
            "WHERE groupId=:groupId ORDER BY MESSAGES.id DESC LIMIT 1;")
    String getGroupLastMessage(long groupId);

    @Query("SELECT message FROM MESSAGES \n" +
            "INNER JOIN PEOPLE ON  PEOPLE.id =  MESSAGES.personId\n" +
            "WHERE groupId IS NULL AND personId=:personId  ORDER BY MESSAGES.id DESC LIMIT 1;")
    String getPersonLastMessage(long personId);

    @Query("DELETE FROM ELITE")
    void deleteElite();

    @Query("DELETE FROM GROUPS")
    void deleteGroups();

    @Query("DELETE FROM GROUP_MEMBERS")
    void deleteGroupMembers();

    @Query("DELETE FROM IMPORTANT")
    void deleteImportantMessages();

    @Query("DELETE FROM MESSAGES")
    void deleteMessages();

    @Query("DELETE FROM PEOPLE")
    void deletePeople();

    @Query("DELETE FROM PINNED")
    void deletePinnedMessages();

    @Query("DELETE FROM SPAM")
    void deleteSpamMessages();

    @Query("DELETE FROM MEDIA")
    void deleteMedia();

    @Query("UPDATE MEDIA SET path=:newPath, isDeleted=1 WHERE path=:prevPath")
    void updateMedia(String prevPath, String newPath);

}
