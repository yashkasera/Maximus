package com.craft404.maximus.ui.group;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.Group;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Group>> getGroups;

    public GroupViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        getGroups = repository.getGroups();
    }

    public LiveData<List<Group>> getGroups() {
        return getGroups;
    }


}