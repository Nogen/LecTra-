package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

import javax.inject.Inject;


@SuppressWarnings("ALL")
public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private SubjectRepository subjectRepository;

    @Inject
    public CustomViewModelFactory(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListItemViewModel.class)) {

            return (T) new ListItemViewModel(subjectRepository);

        } else if (modelClass.isAssignableFrom(DetailItemViewModel.class)) {

            return (T) new DetailItemViewModel(subjectRepository);

        } else if (modelClass.isAssignableFrom(CreateItemViewModel.class)) {

            return (T) new CreateItemViewModel(subjectRepository);

        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {

            return (T) new LoginViewModel(subjectRepository);

        } else {

            throw new IllegalArgumentException("ViewModel Not Found");

        }

    }
}
