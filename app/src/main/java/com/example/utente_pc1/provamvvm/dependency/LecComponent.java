package com.example.utente_pc1.provamvvm.dependency;

import android.app.Application;

import com.example.utente_pc1.provamvvm.ui.create.CreateActivity;
import com.example.utente_pc1.provamvvm.ui.detail.DetailActivity;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;
import com.example.utente_pc1.provamvvm.ui.login.LoginActivity;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DbModule.class, LecModule.class})
public interface LecComponent {

    void inject(LoginActivity loginActivity);

    void inject(Listactivity listactivity);

    void inject(DetailActivity detailActivity);

    void inject(CreateActivity createActivity);

    Application applictaion();
}
