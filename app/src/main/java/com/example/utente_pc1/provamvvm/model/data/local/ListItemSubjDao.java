package com.example.utente_pc1.provamvvm.model.data.local;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import javax.inject.Inject;

@Dao
public interface ListItemSubjDao {


    /*----------------------------------- LIST ITEM --------------------------------*/
    @Query("SELECT * FROM listitemsubj ORDER BY inserttime DESC")
    public LiveData<List<ListItemSubj>> getItemCollection();

    @Query("SELECT * FROM listitemsubj WHERE loginName = :loginName ORDER BY inserttime DESC")
    public LiveData<List<ListItemSubj>> getItemByLogin(String loginName);

    @Query("SELECT * FROM listitemsubj WHERE name = :name")
    public LiveData<List<ListItemSubj>> getItemFilteredCollectionName(String name);

    @Query("SELECT * FROM listitemsubj WHERE date = :date")
    public LiveData<List<ListItemSubj>> getItemFileredCollectionDate(String date);

    @Query("SELECT SUM(hours) FROM listitemsubj WHERE name = :name AND loginName = :user")
    public LiveData<Integer> getTotalHours(String name, String user);

    @Query("SELECT date FROM listitemsubj WHERE name = :name AND loginName = :user")
    public LiveData<List<String>> getDates(String name, String user);

    @Delete
    public void deleteListItemSubj(ListItemSubj listItemSubj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListItemSubj(ListItemSubj listItemSubj);

    /*----------------------------------- GROUPS --------------------------------*/
    @Query("SELECT * FROM groupsubj")
    public LiveData<List<GroupSubj>> getGroups();

    @Query("SELECT totalHours FROM groupsubj WHERE GroupName = :groupName")
    public LiveData<Float> getTotalGrupHours(String groupName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertGroupSubj(GroupSubj groupSubj);

    /*----------------------------------- SINGLE SUBJ --------------------------------*/
    @Query("SELECT * FROM singlesubj")
    public LiveData<List<SingleSubj>> getSubjs();

    @Query("SELECT * FROM singlesubj WHERE groupName = :groupName")
    public LiveData<List<SingleSubj>> getSubjsbyGroup(String groupName);

    @Query("SELECT subjHours FROM singlesubj WHERE subjName = :subjName")
    public LiveData<Float> getSingleSubjHours(String subjName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSingleSubjs(SingleSubj singleSubj);

    @Query("DELETE FROM singlesubj")
    public void deleteAllsubj();

    /*----------------------------------- USERS --------------------------------*/

    @Query("SELECT loginPassword FROM userlogin WHERE loginName = :userName")
    public LiveData<String> isUserThere(String userName);

    @Query("SELECT * FROM userlogin")
    public LiveData<List<UserLogin>> getUserLogins();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(UserLogin userLogin);

}
