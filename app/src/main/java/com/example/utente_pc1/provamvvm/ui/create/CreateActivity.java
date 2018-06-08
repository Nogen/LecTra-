package com.example.utente_pc1.provamvvm.ui.create;


import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;
import com.example.utente_pc1.provamvvm.viewmodel.CreateItemViewModel;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.LecApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class CreateActivity extends AppCompatActivity {
    private final static String USER = "USER";

    @Inject
    CustomViewModelFactory wFactory;
    private Context context;
    private CalendarView calendarView;
    private Spinner spinner;
    private EditText hours;
    private Button addbtn;
    private String curdate;
    private ArrayAdapter<String> adapter;
    private String userName;


    @Override
    public void onBackPressed() {
        finished();
    }

    private void finished() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_create);
        context = this;

        ((LecApplication) getApplication()).getLecComponent().inject(this);

        Intent i = getIntent();
        userName = i.getExtras().getString(USER);

        wFactory.create(CreateItemViewModel.class).getSingleSubj().observe(this, new Observer<List<SingleSubj>>() {
            @Override
            public void onChanged(@Nullable List<SingleSubj> singleSubjs) {
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
                for (SingleSubj s : singleSubjs) {
                    adapter.add(s.getSubjName());
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_activity_create);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.add_item);

        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_activity_create);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayformatted = (dayOfMonth > 9) ? "" + dayOfMonth : "0" + dayOfMonth;
                String monthformatted = (month + 1 > 9) ? "" + (month + 1) : "0" + (month + 1);
                curdate = dayformatted + "/" + monthformatted + "/" + year;
            }
        });


        spinner = (Spinner)findViewById(R.id.spin_subj);


        hours = (EditText)findViewById(R.id.txtcr_hours);

        addbtn = (Button) findViewById(R.id.btncr);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat conv = new SimpleDateFormat("dd/MM/yyyy");
                if (curdate == null) {
                    Date date = new Date(calendarView.getDate());
                    curdate = conv.format(date);
                }
                ListItemSubj tmp = new ListItemSubj();
                try {
                    tmp.setHours(Integer.valueOf(hours.getText().toString()));
                    tmp.setDate(curdate);
                    tmp.setName(spinner.getSelectedItem().toString());
                    tmp.setInserttime(conv.parse(curdate).getTime());
                    tmp.setLoginName(userName);
                    wFactory.create(CreateItemViewModel.class).insertSubj(tmp);
                    startListActivity();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "You have to select all info!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void startListActivity() {
        Intent i = new Intent(this, Listactivity.class);
        i.putExtra(USER, userName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finished();
    }
}
