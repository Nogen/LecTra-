package com.example.utente_pc1.provamvvm.model.repository;

import android.arch.lifecycle.LiveData;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.data.local.UserLogin;
import com.example.utente_pc1.provamvvm.model.data.remote.Esse3Api;
import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;

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

    /*----------------------------------- GROUPS --------------------------------*/
    public LiveData<List<GroupSubj>> getGroups() {
        return this.listItemSubjDao.getGroups();
    }

    public LiveData<Float> getTotalGrupHours(String groupName) {
        return this.listItemSubjDao.getTotalGrupHours(groupName);
    }

    public void insertGroupSubj(GroupSubj groupSubj) {
        this.listItemSubjDao.insertGroupSubj(groupSubj);
    }

    public LiveData<List<GroupSubj>> getNetGroups() throws ConnectionException, LoginException {
        return this.esse3Api.getBlocks();
    }

    /*----------------------------------- SINGLE SUBJ --------------------------------*/
    public LiveData<List<SingleSubj>> getSubjs() {
        return this.listItemSubjDao.getSubjs();
    }

    public LiveData<List<SingleSubj>> getSubjsbyGroup(String groupName) {
        return this.listItemSubjDao.getSubjsbyGroup(groupName);
    }

    public LiveData<Float> getSingleSubjHours(String subjName) {
        return this.listItemSubjDao.getSingleSubjHours(subjName);
    }

    public List<SingleSubj> getNetSingleSubj() throws ConnectionException, LoginException {
        return this.esse3Api.getSubjs();
    }

    public void deleteAllsubj() {
        this.listItemSubjDao.deleteAllsubj();
    }

    public void insertSingleSubjs(SingleSubj singleSubj) {
        this.listItemSubjDao.insertSingleSubjs(singleSubj);
    }

    /*----------------------------------- USERS --------------------------------*/
    public void Login(String name, String password) throws ConnectionException, LoginException {
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
