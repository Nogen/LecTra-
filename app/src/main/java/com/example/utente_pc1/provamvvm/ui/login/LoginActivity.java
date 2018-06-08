package com.example.utente_pc1.provamvvm.ui.login;


import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.data.local.UserLogin;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;

import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.LoginViewModel;

import java.util.List;


import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    private final static String USER = "USER";

    @Inject
    CustomViewModelFactory wFactory;
    LoginViewModel loginViewModel;
    private LifecycleOwner context;
    private EditText nametext;
    private EditText password;
    private Button login;
    private ProgressDialog progressDialog;
    private String accountName;
    private String psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        ((LecApplication) getApplication())
                .getLecComponent()
                .inject(this);

        loginViewModel = wFactory.create(LoginViewModel.class);
        context = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        nametext = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountName = nametext.getText().toString();
                psw = password.getText().toString();
                if (accountName.isEmpty() || psw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You have to complete login form!", Toast.LENGTH_LONG)
                            .show();
                } else {
                    progressDialog.show();
                    loginViewModel.deleteAllsubj();
                    AsyncTask<Void, Void, Void> login = new AsyncTask<Void, Void, Void>() {
                        private String errormessage;

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                loginViewModel.logIn(nametext.getText().toString(), password.getText().toString());
                            } catch (ConnectionException e) {
                                errormessage = e.getMessage();
                            } catch (LoginException e1) {
                                errormessage = e1.getMessage();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            if (errormessage == null) {
                                doafterlogin();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText((Context) context, errormessage, Toast.LENGTH_LONG).show();
                            }
                        }

                    };
                    login.execute();
                }
            }
        });
    }

    private void doafterlogin() {
        UserLogin usertmp = new UserLogin();
        usertmp.setLoginName(accountName);
        usertmp.setLoginPassword(psw);
        loginViewModel.insertUser(usertmp);
        loginViewModel.getSingleSubj().observe(context, new Observer<List<SingleSubj>>() {
            @Override
            public void onChanged(@Nullable List<SingleSubj> singleSubjs) {
                if (singleSubjs != null) {
                    for (SingleSubj single : singleSubjs) {
                        loginViewModel.insertSingleSubj(single);
                    }
                    progressDialog.dismiss();
                    toHomeActivity();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText((Context) context, "Internet Connection Missing!", Toast.LENGTH_LONG);
                }

            }
        });
    }

    private void toHomeActivity() {
        Intent i = new Intent((Context) context, Listactivity.class);
        i.putExtra(USER, accountName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}

