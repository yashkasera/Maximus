package com.craft404.maximus.ui.groupInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.craft404.maximus.Repository;
import com.craft404.maximus.model.Group;
import com.craft404.maximus.model.People;

import java.util.List;

public class GroupInfoViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<People>> list;

    public GroupInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Repository getRepository() {
        return repository;
    }

    public LiveData<Group> getGroup(long groupId) {
        return repository.getGroup(groupId);
    }

    public LiveData<List<People>> getMembers(long groupId) {
        return repository.getMembers(groupId);
    }

    public void updatePerson(People person) {
        repository.updatePerson(person);

    }

    public void updateGroup(Group group) {
        repository.updateGroup(group);

    }
}