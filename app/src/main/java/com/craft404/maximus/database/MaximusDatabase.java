package com.craft404.maximus.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.craft404.maximus.model.EliteMessage;
import com.craft404.maximus.model.Group;
import com.craft404.maximus.model.GroupMember;
import com.craft404.maximus.model.ImportantMessage;
import com.craft404.maximus.model.Media;
import com.craft404.maximus.model.Message;
import com.craft404.maximus.model.People;
import com.craft404.maximus.model.PinnedMessage;
import com.craft404.maximus.model.SpamMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Group.class,
        Message.class,
        EliteMessage.class,
        ImportantMessage.class,
        GroupMember.class,
        People.class,
        PinnedMessage.class,
        SpamMessage.class,
        Media.class
}, version = 1, exportSchema = false)

public abstract class MaximusDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "MaximusDatabase";
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    public static volatile MaximusDatabase INSTANCE = null;
    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                DatabaseDao databaseDao = INSTANCE.databaseDao();
                databaseDao.deleteElite();
                databaseDao.deleteGroups();
                databaseDao.deleteGroupMembers();
                databaseDao.deleteImportantMessages();
                databaseDao.deleteMessages();
                databaseDao.deletePeople();
                databaseDao.deletePinnedMessages();
                databaseDao.deleteSpamMessages();
                databaseDao.deleteMedia();
            });
        }
    };

    public static MaximusDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MaximusDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MaximusDatabase.class, DATABASE_NAME)
//                            .addCallback(callback)
                            .build();
//                            .fallbackToDestructiveMigration()
                }
            }
        }
        return INSTANCE;
    }

    public abstract DatabaseDao databaseDao();
}
