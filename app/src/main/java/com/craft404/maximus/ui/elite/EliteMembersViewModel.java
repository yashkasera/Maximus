package com.craft404.maximus.ui.elite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.People;

import java.util.List;

public class EliteMembersViewModel extends AndroidViewModel {
    private final Repository repository;
    private final Application application;
    public LiveData<List<People>> list;

    public EliteMembersViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new Repository(application);
        list = repository.getElitePeople();
    }

    public LiveData<List<People>> getList() {
        return list;
    }

}