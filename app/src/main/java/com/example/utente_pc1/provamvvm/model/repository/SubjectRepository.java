package com.example.utente_pc1.provamvvm.model.repository;

import android.arch.lifecycle.LiveData;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.data.local.UserLogin;
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

    /*----------------------------------- LIST ITEM --------------------------------*/
    public LiveData<List<ListItemSubj>> getListItemCollection() {
        return this.listItemSubjDao.getItemCollection();
    }

    public LiveData<List<ListItemSubj>> getItemByLogin(String loginName) {
        return this.listItemSubjDao.getItemByLogin(loginName);
    }

    public LiveData<List<ListItemSubj>> getListItemFilteredCollectionName(String name) {
        return this.listItemSubjDao.getItemFilteredCollectionName(name);
    }

    public LiveData<List<ListItemSubj>> getListItemFilteredCollectionDate(String date) {
        return this.listItemSubjDao.getItemFileredCollectionDate(date);
    }

    public LiveData<Integer> getTotalHours(String name, String user) {
        return this.listItemSubjDao.getTotalHours(name, user);
    }

    public LiveData<List<String>> getDates(String name, String user) {
        return this.listItemSubjDao.getDates(name, user);
    }

    public void InsertSubj(ListItemSubj listItemSubj) {
        this.listItemSubjDao.insertListItemSubj(listItemSubj);
    }

    public void deleteSubj(ListItemSubj listItemSubj){
        this.listItemSubjDao.deleteListItemSubj(listItemSubj);
    }

    public void Logout() throws Exception{
        this.esse3Api.Logout();
    }

    /*----------------------------------- GROUPS --------------------------------*/
    public LiveData<List<GroupSubj>> getGroups(String userName) {
        return this.listItemSubjDao.getGroups(userName);
    }

    public LiveData<Float> getTotalGroupHours(String groupName, String userName) {
        return this.listItemSubjDao.getTotalGrupHours(groupName, userName);
    }

    public LiveData<Float> getCurrentHourPerGroup(String groupName, String userName) {
        return this.listItemSubjDao.getCurrentHourPerGroup(groupName, userName);
    }

    public void insertGroupSubj(GroupSubj groupSubj) {
        this.listItemSubjDao.insertGroupSubj(groupSubj);
    }

    public List<GroupSubj> getNetGroups() throws Exception  {
        return this.esse3Api.getBlocks();
    }

    /*----------------------------------- SINGLE SUBJ --------------------------------*/
    public LiveData<List<SingleSubj>> getSubjs(String userName) {
        return this.listItemSubjDao.getSubjs(userName);
    }

    public LiveData<List<SingleSubj>> getSubjsbyGroup(String groupName, String userName) {
        return this.listItemSubjDao.getSubjsbyGroup(groupName, userName);
    }

    public LiveData<Float> getSingleSubjHours(String subjName, String userName) {
        return this.listItemSubjDao.getSingleSubjHours(subjName, userName);
    }

    public List<SingleSubj> getNetSingleSubj() throws Exception {
        return this.esse3Api.getSubjs();
    }

    public void deleteAllsubj(String userName) {
        this.listItemSubjDao.deleteAllsubj(userName);
    }

    public void insertSingleSubjs(SingleSubj singleSubj) {
        this.listItemSubjDao.insertSingleSubjs(singleSubj);
    }

    /*----------------------------------- USERS --------------------------------*/
    public void Login(String name, String password) throws Exception {
        this.esse3Api.Login(name, password);
    }

    public void insertUser(UserLogin userLogin) {
        this.listItemSubjDao.insertUser(userLogin);
    }

    public LiveData<List<UserLogin>> getUserLogins() {
        return this.listItemSubjDao.getUserLogins();
    }

    public LiveData<String> isUserThere(String userName) {
        return this.listItemSubjDao.isUserThere(userName);
    }
}
