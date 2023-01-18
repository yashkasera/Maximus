package com.craft404.maximus.ui.pinned;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.MessageQuery;

import java.util.List;

public class PinnedViewModel extends AndroidViewModel {
    private final Repository repository;
    private final Application application;
    public LiveData<List<MessageQuery>> list;

    public PinnedViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new Repository(application);
        list = repository.getPinnedMessages();
    }

    public LiveData<List<MessageQuery>> getList() {
        return list;
    }

    public void deletePinned(long messageId) {
        repository.deletePinned(messageId);
    }
}