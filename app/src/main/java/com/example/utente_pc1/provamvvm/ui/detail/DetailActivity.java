package com.example.utente_pc1.provamvvm.ui.detail;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
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
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.DetailItemViewModel;

import java.util.List;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {
    private final static String NAME_DETAIL = "NAME_DETAIL";
    private final static String USER = "USER";

    private LayoutInflater layoutInflater;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView texthours;
    private TextView textSubjHours;
    private String userName;


    @Inject
    CustomViewModelFactory vFactory;

    private List<String> datelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_detail);
        ((LecApplication) getApplication())
                .getLecComponent()
                .inject(this);


        layoutInflater = getLayoutInflater();

        recyclerView = (RecyclerView) findViewById(R.id.rec_dates);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_activity_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.detail);


        texthours = (TextView) findViewById(R.id.txtdt_totalhours);
        textSubjHours = (TextView) findViewById(R.id.txtdt_totalsubjhours);

        TextView textname = (TextView) findViewById(R.id.txtv_name);

        DetailItemViewModel detailItemViewModel = vFactory.create(DetailItemViewModel.class);

        Intent i = getIntent();
        String name = i.getExtras().getString(NAME_DETAIL);
        userName = i.getExtras().getString(USER);

        textname.setText(name);

        detailItemViewModel.getTotalHour(name, userName).observe(this, new android.arch.lifecycle.Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    texthours.setText(String.valueOf(integer));
                }
            }
        });

        detailItemViewModel.getSingleSubjHours(name, userName).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                if (aFloat != null) {
                    textSubjHours.setText(" / " + aFloat.toString());
                }
            }
        });

        detailItemViewModel.getDates(name, userName).observe(this, new android.arch.lifecycle.Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (datelist == null) {
                    setData(strings);
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setData(final List<String> datelistpassed) {
        recyclerView.setAdapter(new CustomAdapter());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);

        CustomTask datatask = new CustomTask(new Runnable() {
            @Override
            public void run() {
                datelist = datelistpassed;
            }
        });
        datatask.execute();
    }


    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @NonNull
        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.date_item, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
            String currDate = datelist.get(position);
            holder.textView.setText(currDate);


        }

        @Override
        public int getItemCount() {
            return datelist.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public CustomViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.txtdtrec_dates);
            }
        }
    }


}
