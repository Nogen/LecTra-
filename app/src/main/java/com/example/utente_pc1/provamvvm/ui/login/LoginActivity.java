package com.example.utente_pc1.provamvvm.ui.login;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utente_pc1.provamvvm.R;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView nametext;
    private EditText password;
    private Button login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nametext = (AutoCompleteTextView) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        login = (Button) findViewById(R.id.email_sign_in_button);


    }

}

