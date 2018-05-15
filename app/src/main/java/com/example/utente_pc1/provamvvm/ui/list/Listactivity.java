package com.example.utente_pc1.provamvvm.ui.list;


import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static android.support.transition.Fade.*;

public class Listactivity extends AppCompatActivity {

    private final static String NAME_DETAIL = "NAME_DETAIL";

    @Inject
    CustomViewModelFactory vFactory;
    List<ListItemSubj> listOfData;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);


        ((LecApplication) getApplication()).getLecComponent().inject(this);

        layoutInflater = getLayoutInflater();
        recyclerView = (RecyclerView)findViewById(R.id.rec_list);

        Button fab = (Button) findViewById(R.id.fab_create_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateActivity();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        vFactory.create(ListItemViewModel.class).getListData().observe(this, new Observer<List<ListItemSubj>>() {
                    @Override
                    public void onChanged(@Nullable List<ListItemSubj> listItemSubjs) {
                        if (listOfData == null) {
                            setData(listItemSubjs);
                        }
                    }
                });
    }


    public void startDetailActivity(String name, View view) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(NAME_DETAIL, name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setExitTransition(new Fade(Fade.OUT));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    view.findViewById(R.id.txtv_name), getString(R.string.transition_name)
            );
            startActivity(i, options.toBundle());

        } else {
            startActivity(i);
        }
    }

    public void startCreateActivity() {
        Intent i = new Intent(this, CreateActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    findViewById(R.id.fab_create_new_item), getString(R.string.transition_button)
            );
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
    }


    public void setData(List<ListItemSubj> listOfData) {
        this.listOfData = listOfData;
        recyclerView.setAdapter(new CustomAdapter());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
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
                startDetailActivity(item.getName(), v);

            }
        }
    }


}
