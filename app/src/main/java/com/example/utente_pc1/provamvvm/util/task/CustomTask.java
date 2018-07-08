package com.example.utente_pc1.provamvvm.util.task;

import android.os.AsyncTask;

public class CustomTask extends AsyncTask<Void, Void, Void> {
    private final Runnable doInBackgroundtask;
    private Runnable doOnPostProcessTask;

    public CustomTask(Runnable doInBackgroundtask, Runnable doOnPostProcessTask) {
        this.doInBackgroundtask = doInBackgroundtask;
        this.doOnPostProcessTask = doOnPostProcessTask;
    }

    public CustomTask(Runnable doInBackgroundtask) {
        this.doInBackgroundtask = doInBackgroundtask;
    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        doInBackgroundtask.run();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (doOnPostProcessTask != null) {
            doOnPostProcessTask.run();
        }
    }


}
