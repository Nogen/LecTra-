package com.example.utente_pc1.provamvvm.ui.detail;

import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.DetailItemViewModel;

import java.util.List;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {
    private final static String NAME_DETAIL = "NAME_DETAIL";

    private LayoutInflater layoutInflater;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView texthours;
    private ImageButton imageButton;


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

        imageButton = (ImageButton) findViewById(R.id.btndt_backtomain);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        texthours = (TextView) findViewById(R.id.txtdt_totalhours);

        TextView textname = (TextView) findViewById(R.id.txtdt_name);

        DetailItemViewModel detailItemViewModel = vFactory.create(DetailItemViewModel.class);

        Intent i = getIntent();
        String name = i.getExtras().getString(NAME_DETAIL);

        textname.setText(name);

        detailItemViewModel.getTotalHour(name).observe(this, new android.arch.lifecycle.Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    texthours.setText(String.valueOf(integer));
                }
            }
        });

        detailItemViewModel.getDates(name).observe(this, new android.arch.lifecycle.Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (datelist == null) {
                    setData(strings);
                }
            }
        });



    }

    private void setData(List<String> datelist) {
        this.datelist = datelist;
        recyclerView.setAdapter(new CustomAdapter());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(itemDecoration);
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
