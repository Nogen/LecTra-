package com.example.utente_pc1.provamvvm.model.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SingleSubj {
    @PrimaryKey
    @NonNull
    private String subjName;
    private String groupName;
    private Float subjHours;
    private String userName;


    public SingleSubj() {

    }

    @Ignore
    public SingleSubj(String subjName, String groupName, Float subjHours) {
        this.subjName = subjName;
        this.groupName = groupName;
        this.subjHours = subjHours;
    }


    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Float getSubjHours() {
        return subjHours;
    }

    public void setSubjHours(Float subjHours) {
        this.subjHours = subjHours;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
