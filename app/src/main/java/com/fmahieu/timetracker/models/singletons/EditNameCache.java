package com.fmahieu.timetracker.models.singletons;

public class EditNameCache {

    private static EditNameCache instance;
    public static EditNameCache getInstance() {
        if(instance == null){
            instance = new EditNameCache();
        }
        return instance;
    }

    private String nameChange;

    private EditNameCache(){}

    public String getNameChange() {
        return nameChange;
    }

    public void setNameChange(String nameChange) {
        this.nameChange = nameChange;
    }
}
