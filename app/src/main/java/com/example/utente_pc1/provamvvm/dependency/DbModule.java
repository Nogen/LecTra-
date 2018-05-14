package com.example.utente_pc1.provamvvm.dependency;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.Subjectdb;
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
    SubjectRepository getRepository(ListItemSubjDao dao) {
        return new SubjectRepository(dao);
    }

    @Provides
    @Singleton
    CustomViewModelFactory getViewModelFactory(SubjectRepository repository) {
        return new CustomViewModelFactory(repository);
    }

}
