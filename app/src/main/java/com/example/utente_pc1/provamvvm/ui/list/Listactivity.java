package com.example.utente_pc1.provamvvm.ui.list;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.ui.create.CreateActivity;
import com.example.utente_pc1.provamvvm.ui.detail.DetailActivity;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.ListItemViewModel;


import java.util.List;

import javax.inject.Inject;

public class Listactivity extends AppCompatActivity {

    @Inject
    CustomViewModelFactory vFactory;
    List<ListItemSubj> listOfData;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);


        ((LecApplication) getApplication()).getLecComponent().inject(this);

        layoutInflater = getLayoutInflater();
        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_create_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateActivity();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        vFactory.create(ListItemViewModel.class).getListData().observe(this, new Observer<List<ListItemSubj>>() {
                    @Override
                    public void onChanged(@Nullable List<ListItemSubj> listItemSubjs) {
                        if (listOfData == null) {
                            setData(listItemSubjs);
                        }
                    }
                });
    }


    public void startDetailActivity() {
        startActivity(new Intent(this, DetailActivity.class));
    }

    public void startCreateActivity() {
        startActivity(new Intent(this, CreateActivity.class));
        finish();
    }


    public void setData(List<ListItemSubj> listOfData) {
        this.listOfData = listOfData;
        recyclerView.setAdapter(new CustomAdapter());
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @NonNull
        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
            ListItemSubj currentItem = listOfData.get(position);
            holder.date.setText(currentItem.getDate());
            holder.name.setText(currentItem.getName());
            holder.hours.setText("" + currentItem.getHours());
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }


        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView name;
            private TextView date;
            private TextView hours;
            private ViewGroup container;


            public CustomViewHolder(View itemView) {
                super(itemView);
                this.name = (TextView)itemView.findViewById(R.id.txtv_name);
                this.date = (TextView)itemView.findViewById(R.id.txtv_date);
                this.hours = (TextView)itemView.findViewById(R.id.txtv_hours);
                this.container = (ViewGroup) itemView.findViewById(R.id.root_list_item);
                this.container.setOnClickListener(this);

            }
            @Override
            public void onClick(View v) {
                ListItemSubj item = listOfData.get(this.getAdapterPosition());
                startDetailActivity();

            }
        }
    }


}
