package com.example.utente_pc1.provamvvm.ui.login.offline;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.local.UserLogin;
import com.example.utente_pc1.provamvvm.ui.list.ListActivity;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.LoginViewModel;

import java.util.List;

import javax.inject.Inject;

public class OfflineActivity extends AppCompatActivity {
    private final static String USER = "USER";

    @Inject
    CustomViewModelFactory wFactory;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private LoginViewModel loginViewModel;
    private EditText password;
    private Button signButton;
    private List<UserLogin> loginsData;
    private String userName;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ((LecApplication) getApplication())
                .getLecComponent()
                .inject(this);

        context = this;
        loginViewModel = wFactory.create(LoginViewModel.class);
        layoutInflater = getLayoutInflater();
        password = (EditText) findViewById(R.id.off_password);
        signButton = (Button) findViewById(R.id.off_button);

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String psw = password.getText().toString();
                if (psw == null || userName == null) {
                    Toast.makeText(context,
                            "You have to insert all info!",
                            Toast.LENGTH_SHORT);
                } else {
                    loginViewModel.isUserThere(userName).observe(OfflineActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if (s != null && s.equals(psw)) {
                                toHome();
                            } else {
                                Toast.makeText(context,
                                        "Wrong password!",
                                        Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.offline_rec);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loginViewModel.getUserLogins().observe(this, new Observer<List<UserLogin>>() {
            @Override
            public void onChanged(@Nullable List<UserLogin> userLogins) {
                if (userLogins != null) {
                    setData(userLogins);
                }
            }
        });


    }

    private void toHome() {
        Intent i = new Intent(this, ListActivity.class);
        i.putExtra(USER, userName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void setData(List<UserLogin> listOfData) {
        this.loginsData = listOfData;
        CustomAdapter customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        private ViewGroup master;

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.login_item, parent, false);
            return new CustomViewHolder(v);

        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            UserLogin user = loginsData.get(position);
            holder.accountname.setText(user.getLoginName());
        }

        @Override
        public int getItemCount() {
            return loginsData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ViewGroup masterview;
            private TextView accountname;


            public CustomViewHolder(View itemView) {
                super(itemView);
                masterview = (ViewGroup) itemView.findViewById(R.id.root_login_item);
                accountname = (TextView) itemView.findViewById(R.id.loginUsername);
                masterview.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (master != null && !master.equals(masterview)) {
                    masterview.setSelected(true);
                    master.setSelected(false);
                    master = masterview;
                    userName = accountname.getText().toString();
                    signButton.setEnabled(true);
                    password.setEnabled(true);
                } else if (master == null) {
                    masterview.setSelected(true);
                    master = masterview;
                    userName = accountname.getText().toString();
                    signButton.setEnabled(true);
                    password.setEnabled(true);
                }
            }
        }
    }
}
