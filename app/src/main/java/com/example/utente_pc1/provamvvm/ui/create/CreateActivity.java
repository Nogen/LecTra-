package com.example.utente_pc1.provamvvm.ui.create;


import android.app.ListActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.ui.list.Listactivity;
import com.example.utente_pc1.provamvvm.viewmodel.CreateItemViewModel;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.LecApplication;

import java.text.SimpleDateFormat;

import javax.inject.Inject;


public class CreateActivity extends AppCompatActivity {
    @Inject
    CustomViewModelFactory wFactory;
    private CalendarView calendarView;
    private Spinner spinner;
    private EditText hours;
    private Button addbtn;
    private String curdate;
    private ImageButton imageButton;


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

        ((LecApplication) getApplication()).getLecComponent().inject(this);

        imageButton = (ImageButton) findViewById(R.id.btncr_backtomain);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finished();
            }
        });

        calendarView = (CalendarView)findViewById(R.id.cal_date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day = (dayOfMonth > 9) ? "" + dayOfMonth : "0" + dayOfMonth;
                String mon = ((month + 1) > 9) ? "" + (month + 1) : "0" + (month + 1);
                curdate = day + "/" + mon + "/" + year;
            }
        });

        spinner = (Spinner)findViewById(R.id.spin_subj);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        hours = (EditText)findViewById(R.id.txtcr_hours);

        addbtn = (Button) findViewById(R.id.btncr);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat conv = new SimpleDateFormat("dd/MM/yyyy");
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
        Intent i = new Intent(this, Listactivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finished();
    }
}
