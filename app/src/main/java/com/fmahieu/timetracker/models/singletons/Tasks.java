package com.fmahieu.timetracker.models.singletons;

import android.util.Log;

import com.fmahieu.timetracker.logic.TimeOperationLogic;
import com.fmahieu.timetracker.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tasks {
    private final String TAG = "__Tasks";

    private static Tasks tasks = null;

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

    public void addTask(String name){
        Log.i(TAG, "added a task, num of task: " + this.getNumberOfTasks());
        tasksMap.put(name, new Task(name));
    }

    public String getInitialTime(String taskName){
        if(tasksMap.containsKey(taskName)) {
            return tasksMap.get(taskName).getInitialTime();
        }
        return null;
    }

    public void setInitialTime(String taskName, String time){
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).setInitialTime(time);
        }
    }

    public void resetInitialTime(String taskName){
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).resetInitialTime();
        }
    }

    public String getTotalTime(String taskName){
        if(tasksMap.containsKey(taskName)){
            return tasksMap.get(taskName).getTotalTime();
        }

        return null;
    }

    public void setTotalTime(String taskName, String time){
        if(tasksMap.containsKey(taskName)){
            tasksMap.get(taskName).setTotalTime(time);
        }
    }

    public List<String> getTasksName() {
        return new ArrayList<String>(tasksMap.keySet());
    }

    public int getNumberOfTasks() {
        return this.tasksMap.size();
    }


    public String printTasks(){
        TimeOperationLogic logic = new TimeOperationLogic();
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<String,Task> entry : this.tasksMap.entrySet()){
            strBuilder.append(entry.getKey() + ": " + logic.showDurationAsString( entry.getValue().getTotalTime()) + "\n");
        }
        return strBuilder.toString();
    }

    public boolean doesTaskExist(String taskName) {
        return this.tasksMap.containsKey(taskName);
    }
}
