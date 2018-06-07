package com.example.utente_pc1.provamvvm.util.task;

import android.os.AsyncTask;

public class CustomTask extends AsyncTask<Void, Void, Void> {
    private Runnable doInBackgroundtask;
    private Runnable doOnPostProcessTask;

    public CustomTask(Runnable doInBackgroundtask) {
        this.doInBackgroundtask = doInBackgroundtask;
    }

    public CustomTask(Runnable doInBackgroundtask, Runnable doOnPostProcessTask) {
        this.doInBackgroundtask = doInBackgroundtask;
        this.doOnPostProcessTask = doOnPostProcessTask;
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
