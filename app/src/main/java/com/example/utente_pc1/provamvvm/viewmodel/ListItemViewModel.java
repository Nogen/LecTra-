package com.example.utente_pc1.provamvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.utente_pc1.provamvvm.model.data.ListItemSubj;
import com.example.utente_pc1.provamvvm.model.repository.SubjectRepository;

import java.util.List;

public class ListItemViewModel extends ViewModel {
    private SubjectRepository subjectRepository;


    public ListItemViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<List<ListItemSubj>> getListData() {
        return this.subjectRepository.getListItemCollection();
    }

    public LiveData<List<ListItemSubj>> getListFilteredName(String name) {
        return this.subjectRepository.getListItemFilteredCollectionName(name);
    }

    public LiveData<List<ListItemSubj>> getListFilteredDate(String date) {
        return this.subjectRepository.getListItemFilteredCollectionDate(date);
    }

    public void deleteItem(final ListItemSubj listItemSubj){
        CustomTask t = new CustomTask();
        t.execute(listItemSubj);

    }

    class CustomTask extends AsyncTask<ListItemSubj, Void, Void> {

        @Override
        protected Void doInBackground(ListItemSubj... listItemSubjs) {
            subjectRepository.deleteSubj(listItemSubjs[0]);
            return null;
        }
    }
}
