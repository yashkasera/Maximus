package com.craft404.maximus.ui.important;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.MessageQuery;

import java.util.List;

public class ImportantMessagesViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<MessageQuery>> getImportantMessages;

    public ImportantMessagesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        getImportantMessages = repository.getImportantMessages();
    }

    public LiveData<List<MessageQuery>> getImportantMessages() {
        return getImportantMessages;
    }

    public Repository getRepository() {

        return repository;
    }
}