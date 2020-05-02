package com.fmahieu.timetracker.models;

public class TimePair {

    private String startTime;
    private String endTime;
    private String date;
    private String taskId;

    /**
     * totalTime: total time between start and time of the pair (in seconds).
     */
    private int totalTime;
}
