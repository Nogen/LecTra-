package com.example.utente_pc1.provamvvm.model.repository;

import android.arch.lifecycle.LiveData;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.ListItemSubjDao;

import java.util.List;

import javax.inject.Inject;

public class SubjectRepository {
    private ListItemSubjDao listItemSubjDao;

    @Inject
    public SubjectRepository(ListItemSubjDao listItemSubjDao){
        this.listItemSubjDao = listItemSubjDao;
    }


    public LiveData<List<ListItemSubj>> getListItemCollection() {
        return this.listItemSubjDao.getItemCollection();
    }

    public LiveData<List<ListItemSubj>> getListItemFilteredCollectionName(String name) {
        return this.listItemSubjDao.getItemFilteredCollectionName(name);
    }

    public LiveData<List<ListItemSubj>> getListItemFilteredCollectionDate(String date) {
        return this.listItemSubjDao.getItemFileredCollectionDate(date);
    }

    public LiveData<Integer> getTotalHours(String name) {
        return this.listItemSubjDao.getTotalHours(name);
    }

    public LiveData<List<String>> getDates(String name) {
        return this.getDates(name);
    }

    public void InsertSubj(ListItemSubj listItemSubj) {
        this.listItemSubjDao.insertListItemSubj(listItemSubj);
    }

    public void deleteSubj(ListItemSubj listItemSubj){
        this.listItemSubjDao.deleteListItemSubj(listItemSubj);
    }

}
