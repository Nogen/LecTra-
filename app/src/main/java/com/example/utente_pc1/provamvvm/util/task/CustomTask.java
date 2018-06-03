package com.example.utente_pc1.provamvvm.util.task;

import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;

public class CustomTask extends AsyncTask<Void, Void, Void> {
    private Runnable r;
    public CustomTask(Runnable r) {
        this.r = r;
    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        r.run();
        return null;
    }
}
