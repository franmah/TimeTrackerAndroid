package com.fmahieu.timetracker.models;


public class TimeDay {

    private String date;
    private String taskName;
    private long totalTime; // in seconds
    private String id; // handled by the database (not by the model)

    public TimeDay(String date, String taskName, long totalTime) {
        this.date = date;
        this.taskName = taskName;
        this.totalTime = totalTime;
    }

    public TimeDay(String taskName, String date, long totalTime, String id) {
        this.date = date;
        this.taskName = taskName;
        this.totalTime = totalTime;
        this.id = id;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public String getDate() {
        return date;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
