package com.example.utente_pc1.provamvvm.model.data.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;

@Database(entities = {ListItemSubj.class, GroupSubj.class, SingleSubj.class}, version = 1)
public abstract class Subjectdb extends RoomDatabase {
    public abstract ListItemSubjDao ListItemSubjDao();
}
