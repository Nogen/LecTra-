package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;
import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;
import com.example.utente_pc1.provamvvm.util.task.NetCustomTask;

import java.util.List;

public class ListItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;


    public ListItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<List<ListItemSubj>> getListData() {
        return this.subjectRepository.getListItemCollection();
    }

    public LiveData<List<ListItemSubj>> getListFilteredName(String name) {
        return this.subjectRepository.getListItemFilteredCollectionName(name);
    }

    public LiveData<List<ListItemSubj>> getListFilteredDate(String date) {
        return this.subjectRepository.getListItemFilteredCollectionDate(date);
    }

    public void deleteItem(final ListItemSubj listItemSubj){
        CustomTask t = new CustomTask(new Runnable() {
            @Override
            public void run() {
                subjectRepository.deleteSubj(listItemSubj);
            }
        });
        t.execute();
    }



}
