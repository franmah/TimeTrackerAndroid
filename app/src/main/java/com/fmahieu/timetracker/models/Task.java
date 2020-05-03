package com.fmahieu.timetracker.models;

public class Task {

    private String name;
    private String dateCreated;
    private String totalDuration;
    private String initialTime; // when stopwatch is started

    public Task(){}

    public Task(String name, String dateCreated, String totalDuration) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.totalDuration = totalDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getInitialTime() {
        return initialTime;
    }

    /**
     * If an initial time has already been set but not stopped, it is simply removed for now
     * @param initialTime
     */
    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public void resetInitialTime() {
        this.initialTime = null;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
