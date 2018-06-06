package com.example.utente_pc1.provamvvm.ui.login;


import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;


import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;

import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.ListItemViewModel;

import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    @Inject
    CustomViewModelFactory wFactory;
    ListItemViewModel listItemViewModel;
    private LifecycleOwner context;
    private EditText nametext;
    private EditText password;
    private Button login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);

        ((LecApplication) getApplication())
                .getLecComponent()
                .inject(this);

        listItemViewModel = wFactory.create(ListItemViewModel.class);
        context = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        nametext = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                listItemViewModel.deleteAllsubj();
                listItemViewModel.logIn(nametext.getText().toString(), password.getText().toString());
                listItemViewModel.getSingleSubj().observe(context, new Observer<List<SingleSubj>>() {
                    @Override
                    public void onChanged(@Nullable List<SingleSubj> singleSubjs) {
                        if (singleSubjs != null) {
                            for (SingleSubj single : singleSubjs) {
                                listItemViewModel.insertSingleSubj(single);
                            }
                        }
                        progressDialog.dismiss();
                        Intent i = new Intent((Context) context, Listactivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });


    }

}

