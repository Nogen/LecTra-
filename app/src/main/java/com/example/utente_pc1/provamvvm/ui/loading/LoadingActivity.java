package com.example.utente_pc1.provamvvm.ui.loading;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.ListItemViewModel;

import java.util.List;

import javax.inject.Inject;

public class LoadingActivity extends AppCompatActivity {
    @Inject
    CustomViewModelFactory wFactory;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_loading_activity);

        ((LecApplication) getApplication())
                .getLecComponent()
                .inject(this);


        context = this;
        String name = "i.napoli2";
        String password = "H*97!79*2a";
        wFactory.create(ListItemViewModel.class).deleteAllsubj();
        wFactory.create(ListItemViewModel.class).logIn(name, password);
        wFactory.create(ListItemViewModel.class).getSingleSubj().observe(this, new Observer<List<SingleSubj>>() {
            @Override
            public void onChanged(@Nullable List<SingleSubj> singleSubjs) {
                if (singleSubjs != null) {
                    for (SingleSubj s : singleSubjs) {
                        wFactory.create(ListItemViewModel.class).insertSingleSubj(s);
                    }
                }
                doinit();
            }
        });

    }

    private void doinit() {
        finish();
        Intent i = new Intent(context, Listactivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
