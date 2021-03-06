package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.local.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

import java.util.List;


public class CreateItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public CreateItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public void insertSubj(final ListItemSubj listItemSubj) {
        CustomTask t = new CustomTask();
        t.execute(listItemSubj);
    }

    public LiveData<List<SingleSubj>> getSingleSubj(String userName) {
        return this.subjectRepository.getSubjs(userName);
    }

    class CustomTask extends AsyncTask<ListItemSubj, Void, Void> {

        @Override
        protected Void doInBackground(ListItemSubj... listItemSubjs) {
            subjectRepository.InsertSubj(listItemSubjs[0]);
            return null;
        }
    }
}
