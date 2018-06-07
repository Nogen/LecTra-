package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;
import com.example.utente_pc1.provamvvm.util.task.NetCustomTask;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public LoginViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public void logIn(String name, String password) throws LoginException, ConnectionException {
        subjectRepository.Login(name, password);
    }

    public LiveData<List<SingleSubj>> getSingleSubj() {
        MutableLiveData<List<SingleSubj>> mutableLiveData = new MutableLiveData<>();
        NetCustomTask singlesubjtask = new NetCustomTask(mutableLiveData, subjectRepository);
        singlesubjtask.execute();
        return mutableLiveData;
    }

    public void insertSingleSubj(SingleSubj singleSubj) {
        final SingleSubj tmp = singleSubj;
        CustomTask inserttask = new CustomTask(new Runnable() {
            @Override
            public void run() {
                subjectRepository.insertSingleSubjs(tmp);
            }
        });
        inserttask.execute();
    }

    public void deleteAllsubj() {
        CustomTask deleteall = new CustomTask(new Runnable() {

            @Override
            public void run() {
                subjectRepository.deleteAllsubj();
                return;
            }
        });
        deleteall.execute();
    }
}
