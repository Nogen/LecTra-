package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;

import java.util.List;

public class ListItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;


    public ListItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<List<ListItemSubj>> getListData() {
        return this.subjectRepository.getListItemCollection();
    }

    public LiveData<List<ListItemSubj>> getItemByLogin(String loginName) {
        return this.subjectRepository.getItemByLogin(loginName);
    }

    public LiveData<List<ListItemSubj>> getListFilteredName(String name) {
        return this.subjectRepository.getListItemFilteredCollectionName(name);
    }

    public LiveData<List<ListItemSubj>> getListFilteredDate(String date) {
        return this.subjectRepository.getListItemFilteredCollectionDate(date);
    }

    public void Logout() {
        CustomTask t = new CustomTask(new Runnable() {
            @Override
            public void run() {
                try {
                    subjectRepository.Logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.execute();
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
