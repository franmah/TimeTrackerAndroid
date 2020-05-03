package com.fmahieu.timetracker.logic;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.Task;
import com.fmahieu.timetracker.models.singletons.Tasks;

import java.util.List;

import com.fmahieu.timetracker.logic.TimeDateLogic.TimeOperationLogic;

/**
 * Handles logic to manipulate tasks (called activities within app)
 */
public class TaskLogic {
    final private String TAG = "__TaskLogic";

    private TimeOperationLogic timeOperationLogic;
    private DateOperationLogic dateOperationLogic;
    private Tasks tasks;
    private TaskDao taskDao;

    public TaskLogic(){
        this.timeOperationLogic = new TimeOperationLogic();
        this.dateOperationLogic = new DateOperationLogic();
        this.taskDao = new TaskDao();
        this.tasks = Tasks.getInstance();
    }

    public void addTask(String newTaskName) {
        Log.i(TAG, "adding task: " + newTaskName);
        String dateCreated = dateOperationLogic.getCurrentDateAsString();
        String initialTotalDuration = timeOperationLogic.getDefaultDuration();

        Task task = new Task(newTaskName, dateCreated, initialTotalDuration);
        this.tasks.addTask(task);
        taskDao.addTask(task);
    }

    public boolean doesTaskExist(String taskName){
        return tasks.doesTaskExist(taskName);
    }

    // TODO: move stopwatch functions to their own logic class (StopwatchLogic)
    public void startStopwatchForTask(String taskName){
        Log.i(TAG, "starting stopwatch for: " + taskName);

        String currentTime = timeOperationLogic.getCurrentTime();
        tasks.setInitialTime(taskName, currentTime);
    }

    public void stopStopwatchForTask(String taskName){
        Log.i(TAG, "stopping stopwatch for: " + taskName);

        String endTime = timeOperationLogic.getCurrentTime();
        String initialTime = tasks.getInitialTime(taskName);

        tasks.resetInitialTime(taskName);

        // find how long the timer has been running
        String duration = timeOperationLogic.getDurationBetweenTwoTimes(initialTime, endTime);

        String totalTime = tasks.getTotalTime(taskName);

        Log.i(TAG, duration + " " + totalTime);
        String newTotalTime = timeOperationLogic.sumDurations(totalTime, duration);

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
