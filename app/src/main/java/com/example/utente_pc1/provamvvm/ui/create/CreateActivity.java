package com.example.utente_pc1.provamvvm.ui.create;

import android.app.ListActivity;
import android.arch.persistence.room.Room;
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
import java.util.List;

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
                curdate = dayOfMonth +"/" + month + "/" + year;
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
                ListItemSubj tmp = new ListItemSubj();
                tmp.setHours(Integer.valueOf(hours.getText().toString()));
                tmp.setDate(curdate);
                tmp.setName(spinner.getSelectedItem().toString());
                wFactory.create(CreateItemViewModel.class).insertSubj(tmp);
                startListActivity();
            }
        });
    }

    public void startListActivity() {
        startActivity(new Intent(this, Listactivity.class));
    }
}
