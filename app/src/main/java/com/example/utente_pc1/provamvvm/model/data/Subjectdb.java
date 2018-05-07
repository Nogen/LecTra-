package com.example.utente_pc1.provamvvm.model.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ListItemSubj.class}, version = 1)
public abstract class Subjectdb extends RoomDatabase {
    public abstract ListItemSubjDao ListItemSubjDao();
}
