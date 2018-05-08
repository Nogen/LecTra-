package com.example.utente_pc1.provamvvm.ui.create;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.ListItemSubjDao;
import com.example.utente_pc1.provamvvm.model.data.Subjectdb;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;
import com.example.utente_pc1.provamvvm.util.DbGuardian;
import com.example.utente_pc1.provamvvm.viewmodel.CreateItemViewModel;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;

import java.text.SimpleDateFormat;


public class CreateActivity extends AppCompatActivity {
    private Subjectdb db;
    private ListItemSubjDao dao;
    private SubjectRepository repository;
    private CustomViewModelFactory wFactory;
    private CalendarView calendarView;
    private Spinner spinner;
    private EditText hours;
    private Button addbtn;
    private String curdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        calendarView = (CalendarView)findViewById(R.id.cal_date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curdate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        spinner = (Spinner)findViewById(R.id.spin_subj);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        hours = (EditText)findViewById(R.id.txtcr_hours);

        addbtn = (Button) findViewById(R.id.btncr);

        db = DbGuardian.subjectdb;
        dao = db.ListItemSubjDao();
        repository = new SubjectRepository(dao);
        wFactory = new CustomViewModelFactory(repository);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat conv = new SimpleDateFormat("dd/MM/yy");
                String defdate = conv.format(calendarView.getDate());
                ListItemSubj tmp = new ListItemSubj();
                try {
                    tmp.setHours(Integer.valueOf(hours.getText().toString()));
                    tmp.setDate((curdate != null) ? curdate : defdate);
                    tmp.setName(spinner.getSelectedItem().toString());
                    wFactory.create(CreateItemViewModel.class).insertSubj(tmp);
                    startListActivity();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "You have to select all info!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void startListActivity() {
        startActivity(new Intent(this, Listactivity.class));
    }
}
