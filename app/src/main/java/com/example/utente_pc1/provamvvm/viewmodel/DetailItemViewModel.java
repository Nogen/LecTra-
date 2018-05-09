package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

import java.util.List;

public class DetailItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public DetailItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<Integer> getTotalHour(String name) {
        return this.subjectRepository.getTotalHours(name);
    }

    public LiveData<List<String>> getDates(String name) {
        return this.subjectRepository.getDates(name);
    }

}
