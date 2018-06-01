package com.example.utente_pc1.provamvvm.util;

import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;

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
