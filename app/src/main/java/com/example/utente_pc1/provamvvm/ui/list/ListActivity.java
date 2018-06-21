package com.example.utente_pc1.provamvvm.ui.list;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.utente_pc1.provamvvm.R;
import com.example.utente_pc1.provamvvm.LecApplication;
import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.ui.create.CreateActivity;
import com.example.utente_pc1.provamvvm.ui.detail.DetailActivity;
import com.example.utente_pc1.provamvvm.ui.login.LoginActivity;
import com.example.utente_pc1.provamvvm.util.task.CustomTask;
import com.example.utente_pc1.provamvvm.viewmodel.CustomViewModelFactory;
import com.example.utente_pc1.provamvvm.viewmodel.ListItemViewModel;


import java.util.List;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity {
    private final static String NAME_DETAIL = "NAME_DETAIL";
    private final static String USER = "USER";

    @Inject
    CustomViewModelFactory vFactory;
    List<ListItemSubj> listOfData;
    private LayoutInflater layoutInflater;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomAdapter customAdapter;
    private Context context;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);


        ((LecApplication) getApplication()).getLecComponent().inject(this);


        context = this;
        Intent i = getIntent();
        userName = i.getExtras().getString(USER);

        layoutInflater = getLayoutInflater();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                menuAction(item.getItemId());
                return true;
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_list_activity);
        setSupportActionBar(toolbar);

        ActionBar tool = getSupportActionBar();
        tool.setTitle(R.string.list_of_lessons);
        tool.setDisplayHomeAsUpEnabled(true);
        tool.setHomeAsUpIndicator(R.drawable.ic_drawermenu_24dp);



        recyclerView = (RecyclerView) findViewById(R.id.rec_list);

        Button fab = (Button) findViewById(R.id.fab_create_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateActivity();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        vFactory.create(ListItemViewModel.class).getItemByLogin(userName).observe(this, new Observer<List<ListItemSubj>>() {
            @Override
            public void onChanged(@Nullable List<ListItemSubj> listItemSubjs) {
                if (listOfData == null) {
                    setData(listItemSubjs);
                }
            }
        });
    }


    private void menuAction(int id) {
        switch (id) {
            case R.id.action_logout:
            case R.id.nav_logout:
                vFactory.create(ListItemViewModel.class).Logout();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return true;
    }

    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        menuAction(id);
        return super.onOptionsItemSelected(item);
    }


    public void startDetailActivity(String name, View view) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(NAME_DETAIL, name);
        i.putExtra(USER, userName);

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
        i.putExtra(USER, userName);

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


    public void setData(final List<ListItemSubj> listOfData) {
        this.listOfData = listOfData;
        customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(getDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

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
            holder.hours.setText(String.valueOf(currentItem.getHours()));
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
                this.name = (TextView) itemView.findViewById(R.id.txtv_name);
                this.date = (TextView) itemView.findViewById(R.id.txtv_date);
                this.hours = (TextView) itemView.findViewById(R.id.txtv_hours);
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


    private ItemTouchHelper.Callback getDeleteCallback() {
        ItemTouchHelper.SimpleCallback itemThelper = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
                final ListItemSubj tmp = listOfData.get(pos);
                listOfData.remove(pos);
                customAdapter.notifyItemRemoved(pos);
                Snackbar.make(findViewById(R.id.root_list_item), R.string.delete_item, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listOfData.add(pos, tmp);
                                customAdapter.notifyItemInserted(pos);
                            }

                        })
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                    vFactory.create(ListItemViewModel.class).deleteItem(tmp);
                                }
                            }
                        })
                        .show();

            }
        };
        return itemThelper;
    }


}
