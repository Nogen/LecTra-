package com.example.utente_pc1.provamvvm.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.utente_pc1.provamvvm.model.data.Subjectdb;

public class DbGuardian {
    public static Subjectdb subjectdb;


    public static void init(Context context){
        subjectdb =  Room.databaseBuilder(context,
                Subjectdb.class, "LecTraDb").build();
    }
}
