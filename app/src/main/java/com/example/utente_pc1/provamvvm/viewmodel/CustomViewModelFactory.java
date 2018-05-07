package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;


public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private SubjectRepository subjectRepository;

    public CustomViewModelFactory(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListItemViewModel.class)) {

            return (T) new ListItemViewModel(subjectRepository);

        } else if (modelClass.isAssignableFrom(DetailItemViewModel.class)) {

            return (T) new DetailItemViewModel(subjectRepository);

        } else if (modelClass.isAssignableFrom(CreateItemViewModel.class)) {

            return (T) new CreateItemViewModel(subjectRepository);

        } else {

            throw new IllegalArgumentException("ViewModel Not Found");

        }

    }
}
