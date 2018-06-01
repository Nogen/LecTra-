package com.example.utente_pc1.provamvvm.dependency;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.local.Subjectdb;
import com.example.utente_pc1.provamvvm.model.data.remote.Esse3Api;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    Subjectdb db;

    public DbModule(Application application) {
        db = Room.databaseBuilder(application,
                Subjectdb.class,
                "LecTraDb").build();
    }

    @Provides
    @Singleton
    Esse3Api getEsse3Api() {
        return new Esse3Api();
    }

    @Provides
    @Singleton
    Subjectdb getDb(Application application) {
        return db;
    }

    @Provides
    @Singleton
    ListItemSubjDao getDao(Subjectdb db) {
        return db.ListItemSubjDao();
    }

    @Provides
    @Singleton
    SubjectRepository getRepository(ListItemSubjDao dao, Esse3Api api) {
        return new SubjectRepository(dao, api);
    }

    @Provides
    @Singleton
    CustomViewModelFactory getViewModelFactory(SubjectRepository repository) {
        return new CustomViewModelFactory(repository);
    }

}
