package com.example.utente_pc1.provamvvm.model.repository;

import android.arch.lifecycle.LiveData;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.data.remote.Esse3Api;

import java.util.List;

import javax.inject.Inject;

public class SubjectRepository {
    private ListItemSubjDao listItemSubjDao;
    private Esse3Api esse3Api;

    @Inject
    public SubjectRepository(ListItemSubjDao listItemSubjDao, Esse3Api api) {
        this.listItemSubjDao = listItemSubjDao;
        this.esse3Api = api;
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
        return this.listItemSubjDao.getDates(name);
    }

    public void InsertSubj(ListItemSubj listItemSubj) {
        this.listItemSubjDao.insertListItemSubj(listItemSubj);
    }

    public void deleteSubj(ListItemSubj listItemSubj){
        this.listItemSubjDao.deleteListItemSubj(listItemSubj);
    }

    public LiveData<List<GroupSubj>> getGroups() {
        return this.listItemSubjDao.getGroups();
    }

    public LiveData<Float> getTotalGrupHours(String groupName) {
        return this.listItemSubjDao.getTotalGrupHours(groupName);
    }

    public LiveData<List<SingleSubj>> getSubjs() {
        return this.listItemSubjDao.getSubjs();
    }


    public LiveData<List<SingleSubj>> getSubjsbyGroup(String groupName) {
        return this.listItemSubjDao.getSubjsbyGroup(groupName);
    }


    public LiveData<Float> getSingleSubjHours(String subjName) {
        return this.getSingleSubjHours(subjName);
    }


    public void insertSingleSubjs(SingleSubj singleSubj) {
        this.listItemSubjDao.insertSingleSubjs(singleSubj);
    }


    public void insertGroupSubj(GroupSubj groupSubj) {
        this.listItemSubjDao.insertGroupSubj(groupSubj);
    }

}
