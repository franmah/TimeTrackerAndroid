package com.fmahieu.timetracker.models;

import java.time.Duration;

public class Task {

    private String name;
    private String totalTime;
    private String initialTime; // when timer is started

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
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
}
