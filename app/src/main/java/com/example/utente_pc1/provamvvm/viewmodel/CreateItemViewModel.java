package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;


public class CreateItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;

    public CreateItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public void insertSubj(final ListItemSubj listItemSubj) {
        CustomTask t = new CustomTask();
        t.execute(listItemSubj);
    }

    class CustomTask extends AsyncTask<ListItemSubj, Void, Void> {

        @Override
        protected Void doInBackground(ListItemSubj... listItemSubjs) {
            Log.e("PORCO", listItemSubjs[0].toString());
            subjectRepository.InsertSubj(listItemSubjs[0]);
            return null;
        }
    }
}
