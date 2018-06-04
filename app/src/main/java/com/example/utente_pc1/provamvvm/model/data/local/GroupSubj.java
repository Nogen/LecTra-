package com.example.utente_pc1.provamvvm.model.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class GroupSubj {
    @PrimaryKey
    @NonNull
    private String GroupName;
    private Float totalHours;

    public GroupSubj() {
    }

    public GroupSubj(@NonNull String groupName, Float totalHours) {
        GroupName = groupName;
        this.totalHours = totalHours;
    }


    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Float totalHours) {
        this.totalHours = totalHours;
    }
}
