package com.craft404.maximus.ui.chats;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.Group;
import com.craft404.maximus.model.MessageQuery;
import com.craft404.maximus.model.PinnedMessage;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private static final String TAG = "ChatViewModel";
    Repository repository;
    private LiveData<List<MessageQuery>> list;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Repository getRepository() {
        return repository;
    }

    public LiveData<List<MessageQuery>> getList(long groupId) {
        list = repository.getGroupMessages(groupId);
        return list;
    }

    public void addPinned(long id) {
        repository.insertPinned(new PinnedMessage(id));
    }

    public void removePinned(long id) {
        repository.deletePinned(id);
    }

    public LiveData<Group> getGroup(long groupId) {
        return repository.getGroup(groupId);
    }

    public void updateGroup(Group group) {
        repository.updateGroup(group);
    }

}