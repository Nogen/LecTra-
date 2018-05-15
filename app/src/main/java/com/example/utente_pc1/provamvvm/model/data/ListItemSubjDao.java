package com.example.utente_pc1.provamvvm.model.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface ListItemSubjDao {

    @Query("SELECT * FROM listitemsubj ORDER BY date DESC")
    public LiveData<List<ListItemSubj>> getItemCollection();

    @Query("SELECT * FROM listitemsubj WHERE name = :name")
    public LiveData<List<ListItemSubj>> getItemFilteredCollectionName(String name);

    @Query("SELECT * FROM listitemsubj WHERE date = :date")
    public LiveData<List<ListItemSubj>> getItemFileredCollectionDate(String date);

    @Query("SELECT SUM(hours) FROM listitemsubj WHERE name = :name")
    public LiveData<Integer> getTotalHours(String name);

    @Query("SELECT date FROM listitemsubj WHERE name = :name")
    public LiveData<List<String>> getDates(String name);

    @Delete
    public void deleteListItemSubj(ListItemSubj listItemSubj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListItemSubj(ListItemSubj listItemSubj);
}
