package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.models.singletons.Tasks;

import java.util.List;

import com.fmahieu.timetracker.TimeDateLogic.TimeOperationLogic;

/**
 * Handles logic to manipulate tasks
 */
public class TaskLogic {
    final private String TAG = "__TaskLogic";

    private TimeOperationLogic timeOperationLogic;
    private Tasks tasks;

    public TaskLogic(){
        this.timeOperationLogic = new TimeOperationLogic();
        this.tasks = Tasks.getInstance();
    }

    public void addTask(String newTaskName) {
        this.tasks.addTask(newTaskName);
    }

    public boolean doesTaskExist(String taskName){
        return tasks.doesTaskExist(taskName);
    }

    public void startTimerForTask(String taskName){
        Log.i(TAG, "starting stopwatch for: " + taskName);

        String currentTime = timeOperationLogic.getCurrentTime();
        tasks.setInitialTime(taskName, currentTime);
    }

    public void stopTimerForTask(String taskName){
        Log.i(TAG, "stopping stopwatch for: " + taskName);

        String endTime = timeOperationLogic.getCurrentTime();
        String initialTime = tasks.getInitialTime(taskName);

        tasks.resetInitialTime(taskName);

        // find how long the timer has been running
        String duration = timeOperationLogic.getDurationTime(initialTime, endTime);

        String totalTime = tasks.getTotalTime(taskName);

        Log.i(TAG, duration + " " + totalTime);
        String newTotalTime = timeOperationLogic.addDurationToTime(totalTime, duration);

        Log.i(TAG, "new total time of : " + newTotalTime + " for task: " + taskName);
        tasks.setTotalTime(taskName, newTotalTime);
    }

    public List<String> getTasksNames() {
        return this.tasks.getTasksName();
    }

    public int getNumberOfTasks() {
        return this.tasks.getNumberOfTasks();
    }

    public String getTaskTotalTime(String taskName){
        return tasks.getTotalTime(taskName);
    }

}
