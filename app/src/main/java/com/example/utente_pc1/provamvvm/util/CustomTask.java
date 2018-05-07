package com.example.utente_pc1.provamvvm.util;

import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;


import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CustomTask extends AsyncTask<ListItemSubj, Void, Void> {
    private Runnable r;
    public CustomTask(Runnable r) {
        this.r = r;
    }

    @Override
    protected Void doInBackground(ListItemSubj... listItemSubjs) {
        r.run();
        return null;
    }
}
