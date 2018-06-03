package com.example.utente_pc1.provamvvm.util.task;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;
import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;

import java.util.List;

public class NetCustomTask extends AsyncTask<Void, Void, Void> {
    private MutableLiveData<List<SingleSubj>> mutableLiveData;
    private SubjectRepository repository;
    private List<SingleSubj> datalist;

    public NetCustomTask(MutableLiveData<List<SingleSubj>> mutableLiveData, SubjectRepository repository) {
        this.mutableLiveData = mutableLiveData;
        this.repository = repository;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            datalist = repository.getNetSingleSubj();
        } catch (LoginException e) {

        } catch (ConnectionException e2) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.mutableLiveData.setValue(this.datalist);
    }
}
