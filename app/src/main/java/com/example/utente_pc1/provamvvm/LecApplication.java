package com.example.utente_pc1.provamvvm;

import android.app.Application;

import com.example.utente_pc1.provamvvm.dependency.DaggerLecComponent;
import com.example.utente_pc1.provamvvm.dependency.DbModule;
import com.example.utente_pc1.provamvvm.dependency.LecComponent;
import com.example.utente_pc1.provamvvm.dependency.LecModule;

public class LecApplication extends Application {
    private LecComponent lecComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        lecComponent = DaggerLecComponent
                .builder()
                .lecModule(new LecModule(this))
                .dbModule(new DbModule(this))
                .build();
    }

    public LecComponent getLecComponent() {
        return this.lecComponent;
    }
}
