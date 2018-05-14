package com.example.utente_pc1.provamvvm.dependency;

import android.app.Application;

import com.example.utente_pc1.provamvvm.LecApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LecModule {
    private LecApplication mapplication;

    public LecModule(LecApplication application) {
        this.mapplication = application;
    }

    @Provides
    Application getApplication() {
        return mapplication;
    }


}
