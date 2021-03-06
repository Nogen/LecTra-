package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.data.local.UserLogin;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;
import com.example.utente_pc1.provamvvm.util.task.NetCustomTask;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public LoginViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public void logIn(String name, String password) throws Exception {
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

    public void insertUser(UserLogin userLogin) {
        final UserLogin tmp = userLogin;
        CustomTask insertlogin = new CustomTask(new Runnable() {
            @Override
            public void run() {
                subjectRepository.insertUser(tmp);
            }
        });
        insertlogin.execute();
    }

    public void deleteAllsubj(final String userName) {
        CustomTask deleteall = new CustomTask(new Runnable() {

            @Override
            public void run() {
                subjectRepository.deleteAllsubj(userName);
            }
        });
        deleteall.execute();
    }

    public LiveData<List<UserLogin>> getUserLogins() {
        return this.subjectRepository.getUserLogins();
    }

    public LiveData<String> isUserThere(String userName) {
        return this.subjectRepository.isUserThere(userName);
    }
}
