package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

public class DetailItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public DetailItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<ListItemSubj> getItem(String name, String date) {
        return subjectRepository.getItem(name, date);
    }

}
