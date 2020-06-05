package com.fmahieu.timetracker.models;

public class Task {


    private String name;

    /**
     * stored only with days/years/months (without special order)
     */
    private String dateCreated;
    private String totalDuration;
    // TODO: startTime should have the date and the time.
    private String startTime; // when the stopwatch started (actual hours/minutes/seconds)

    public Task(){}

    public Task(String name, String dateCreated, String totalDuration) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.totalDuration = totalDuration;
        startTime = null;
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

    public String getStartTime() {
        return startTime;
    }

    /**
     * If an initial time has already been set but not stopped, it is simply removed for now
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void resetInitialTime() {
        this.startTime = null;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public boolean isRunning(){
        return startTime != null;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
