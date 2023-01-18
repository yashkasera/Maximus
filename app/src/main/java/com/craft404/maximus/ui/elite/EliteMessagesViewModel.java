package com.craft404.maximus.ui.elite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.MessageQuery;

import java.util.List;

public class EliteMessagesViewModel extends AndroidViewModel {
    private final Repository repository;
    public LiveData<List<MessageQuery>> list;

    public EliteMessagesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<MessageQuery>> getEliteMessages() {
        list = repository.getEliteMessages();
        return list;
    }

    public LiveData<List<MessageQuery>> getList(long personId) {
        list = repository.getPersonMessages(personId);
        return list;
    }

    public Repository getRepository() {
        return repository;
    }

}