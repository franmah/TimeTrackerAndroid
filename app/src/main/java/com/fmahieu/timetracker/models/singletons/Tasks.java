package com.fmahieu.timetracker.models.singletons;

import android.util.Log;

import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tasks {
    private final String TAG = "__Tasks";

    private static Tasks tasks = null;
    private boolean hasLoaded = false;

    public static Tasks getInstance(){
        if(tasks == null) {
            tasks = new Tasks();
        }
        return tasks;
    }

    private Map<String, Task> tasksMap; // <Name of task, task>

    private Tasks() {
        this.tasksMap = new HashMap<>();
    }

    public void addTask(Task task){
        tasksMap.put(task.getName(), task);
        Log.i(TAG, "added a task, num of task: " + this.getNumberOfTasks());
    }

    //TODO: remove most function and make a getTask method
    public String getStartTime(String taskName){
        if(tasksMap.containsKey(taskName)) {
            return tasksMap.get(taskName).getStartTime();
        }
        return null;
    }

    public void setStartTime(String taskName, String time){
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).setStartTime(time);
        }
    }

    public void resetStartTime(String taskName) {
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).resetInitialTime();
        }
    }

    public String getTotalTime(String taskName){
        if(tasksMap.containsKey(taskName)){
            return tasksMap.get(taskName).getTotalDuration();
        }

        return null;
    }

    public void setTotalTime(String taskName, String time){
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).setTotalDuration(time);
        }
    }

    public List<String> getTaskNames() {
        return new ArrayList<String>(tasksMap.keySet());
    }

    public int getNumberOfTasks() {
        return this.tasksMap.size();
    }

    public String printTasks(){
        DateTimeOperationLogic logic = new DateTimeOperationLogic();
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<String,Task> entry : this.tasksMap.entrySet()){
            strBuilder.append(entry.getKey()).append(": ")
                    .append(logic.convertDurationToReadableString(entry.getValue().getTotalDuration()))
                    .append("\n");
        }
        return strBuilder.toString();
    }

    public boolean doesTaskExist(String taskName) {
        return this.tasksMap.containsKey(taskName);
    }

    public void loadTasks(List<Task> tasks){
        Log.i(TAG, "loading tasks into map");
        for(int i = 0; i < tasks.size(); i++) {
            Log.i(TAG, "task being loaded: " + tasks.get(i).getName());
            tasksMap.put(tasks.get(i).getName(), tasks.get(i));
        }
        this.hasLoaded = true;
    }

    public boolean hasLoaded() {
        return this.hasLoaded;
    }

    public boolean isTaskRunning(String taskName){
        return tasksMap.get(taskName).isRunning();
    }

    public void removeTask(String taskName) {
        if(tasksMap.containsKey(taskName)) {
            tasksMap.remove(taskName);
        }
    }

    public Task getTask(String name){
        return tasksMap.get(name);
    }

    public void changeTaskName(String oldName, String newName) {
        Log.i(TAG, "changing " + oldName + " to " + newName);
        if(tasksMap.containsKey(oldName)) {
            Task task = tasksMap.get(oldName);
            task.setName(newName);
            removeTask(oldName);
            addTask(task);
        }
    }
}
