package com.example.utente_pc1.provamvvm.model.data;

public class SingleSubj {
    private String subjName;
    private String groupName;
    private String subjHours;


    public SingleSubj(String subjName, String groupName, String subjHours) {
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

    public String getSubjHours() {
        return subjHours;
    }

    public void setSubjHours(String subjHours) {
        this.subjHours = subjHours;
    }
}
