package com.example.utente_pc1.provamvvm.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GroupSubj {
    @PrimaryKey
    private String GroupName;
    private Float totalHours;

    public GroupSubj(String groupName, Float totalHours) {
        this.GroupName = groupName;
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
