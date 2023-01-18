package com.craft404.maximus.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.craft404.maximus.Repository;

public class SettingsViewModel extends AndroidViewModel {
    private final Repository repository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void deleteAllMessages() {
        repository.deleteAllMessages();
    }

    public void resetSpammers() {
        repository.resetSpammers();
    }

    public void resetElite() {
        repository.resetElitePeople();
    }
}
