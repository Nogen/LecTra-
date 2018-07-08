package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

import java.util.List;

public class GroupItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public GroupItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<List<GroupSubj>> getGroups(String userName) {
        return this.subjectRepository.getGroups(userName);
    }

    public LiveData<Float> getTotalGrupHours(String groupName, String userName) {
        return this.subjectRepository.getTotalGroupHours(groupName, userName);
    }

    public LiveData<Float> getCurrentHourPerGroup(String groupName, String userName) {
        return this.subjectRepository.getCurrentHourPerGroup(groupName, userName);
    }

    public LiveData<List<SingleSubj>> getSubjsbyGroup(String groupName, String userName) {
        return this.subjectRepository.getSubjsbyGroup(groupName, userName);
    }


}
