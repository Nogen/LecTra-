package com.example.utente_pc1.provamvvm.model.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"name", "date"})
public class ListItemSubj {
    private @NonNull
    String name;
    private @NonNull
    String date;
    private Integer hours;
    private Long inserttime;
    private String loginName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Long getInserttime() {
        return inserttime;
    }

    public void setInserttime(Long inserttime) {
        this.inserttime = inserttime;
    }

    @Override
    public String toString() {
        return name + " : " + date + " : " + String.valueOf(hours);
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
